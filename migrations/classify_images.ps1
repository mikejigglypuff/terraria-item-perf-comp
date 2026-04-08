# PowerShell 스크립트: 이미지 파일들을 카테고리에 따라 분류

$itemsDir = "docs\img\Terraria_Items"
$sqlFile = "migrations\insert_items_list.sql"

# 카테고리 매핑
$categoryMap = @{
    1 = "melee"
    2 = "ranged"
    3 = "magic"
    4 = "summon"
}

# SQL 파일 읽기
$sqlContent = Get-Content -Path $sqlFile -Raw -Encoding UTF8

# INSERT 문에서 category_id 추출하여 아이템 ID 매핑 생성
$itemCategoryMap = @{}
$itemId = 1

# INSERT INTO `items` ... VALUES (... category_id ...);
$pattern = "INSERT INTO `items`[^)]+VALUES\s*\([^,]+,\s*(?:'[^']+',\s*)?(\d+),\s*(\d+),\s*\d+\);"
$matches = [regex]::Matches($sqlContent, $pattern)

foreach ($match in $matches) {
    $categoryId = [int]$match.Groups[2].Value
    $itemCategoryMap[$itemId] = $categoryId
    $itemId++
}

Write-Host "SQL에서 $($itemCategoryMap.Count)개의 아이템 정보를 추출했습니다."

# 카테고리별 폴더 생성
foreach ($categoryId in $categoryMap.Keys) {
    $categoryDir = Join-Path $itemsDir $categoryMap[$categoryId]
    if (-not (Test-Path $categoryDir)) {
        New-Item -ItemType Directory -Path $categoryDir -Force | Out-Null
    }
}

# others 폴더 생성
$othersDir = Join-Path $itemsDir "others"
if (-not (Test-Path $othersDir)) {
    New-Item -ItemType Directory -Path $othersDir -Force | Out-Null
}

# 이미지 파일 분류
$imageFiles = Get-ChildItem -Path $itemsDir -Filter "Item_*.png" -File
$movedCount = 0
$notFoundCount = 0

foreach ($imageFile in $imageFiles) {
    # Item_숫자.png에서 숫자 추출
    if ($imageFile.Name -match "Item_(\d+)\.png") {
        $itemId = [int]$matches[1]
        
        if ($itemCategoryMap.ContainsKey($itemId)) {
            $categoryId = $itemCategoryMap[$itemId]
            $categoryName = $categoryMap[$categoryId]
            $targetDir = Join-Path $itemsDir $categoryName
            $targetPath = Join-Path $targetDir $imageFile.Name
            
            if (-not (Test-Path $targetPath)) {
                Move-Item -Path $imageFile.FullName -Destination $targetPath -Force
                $movedCount++
            }
        } else {
            # 매핑이 없는 경우 others 폴더로 이동
            $targetPath = Join-Path $othersDir $imageFile.Name
            if (-not (Test-Path $targetPath)) {
                Move-Item -Path $imageFile.FullName -Destination $targetPath -Force
                $notFoundCount++
            }
        }
    }
}

Write-Host "분류 완료!"
Write-Host "- 이동된 파일: $movedCount 개"
Write-Host "- others 폴더로 이동: $notFoundCount 개"

