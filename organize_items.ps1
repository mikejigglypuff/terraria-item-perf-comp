# PowerShell 스크립트로 이미지 파일 분류
$sqlFile = "migrations\insert_items_list.sql"
$itemsDir = "docs\img\Terraria_Items"

# 카테고리 매핑
$categoryMap = @{
    1 = "Melee"
    2 = "Ranged"
    3 = "Magic"
    4 = "Summon"
}

# 카테고리 폴더 생성
foreach ($categoryName in $categoryMap.Values) {
    $categoryPath = Join-Path $itemsDir $categoryName
    if (-not (Test-Path $categoryPath)) {
        New-Item -ItemType Directory -Path $categoryPath | Out-Null
    }
}

# SQL 파일 읽기 및 아이템 카테고리 매핑 생성
$itemCategories = @{}
$itemId = 1

Get-Content $sqlFile | ForEach-Object {
    if ($_ -match "INSERT INTO.*category_id`,`title_id`\) VALUES.*?, (\d+), \d+") {
        $categoryId = [int]$matches[1]
        $itemCategories[$itemId] = $categoryId
        $itemId++
    }
}

Write-Host "Total items found in SQL: $($itemCategories.Count)"

# 이미지 파일 이동
$movedCount = 0
$notFoundCount = 0

foreach ($itemId in $itemCategories.Keys) {
    $imageFile = Join-Path $itemsDir "Item_$itemId.png"
    $categoryId = $itemCategories[$itemId]
    $categoryName = $categoryMap[$categoryId]
    
    if (Test-Path $imageFile) {
        $targetDir = Join-Path $itemsDir $categoryName
        $targetPath = Join-Path $targetDir "Item_$itemId.png"
        Move-Item -Path $imageFile -Destination $targetPath -Force
        $movedCount++
    } else {
        $notFoundCount++
        if ($notFoundCount -le 10) {
            Write-Host "Image not found: Item_$itemId.png"
        }
    }
}

Write-Host "`nMoved $movedCount images"
Write-Host "Not found: $notFoundCount images"

# 카테고리별 파일 개수 출력
Write-Host "`nFiles per category:"
foreach ($categoryName in $categoryMap.Values) {
    $categoryPath = Join-Path $itemsDir $categoryName
    if (Test-Path $categoryPath) {
        $count = (Get-ChildItem -Path $categoryPath -Filter "*.png").Count
        Write-Host "  $categoryName : $count files"
    }
}

