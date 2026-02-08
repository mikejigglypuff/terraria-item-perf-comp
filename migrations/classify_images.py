import os
import re
import shutil
from pathlib import Path

# 카테고리 매핑 (위키 페이지 구조 기반)
# category_id: 1=Melee, 2=Ranged, 3=Magic, 4=Summon
CATEGORY_NAMES = {
    1: 'melee',
    2: 'ranged',
    3: 'magic',
    4: 'summon'
}

# SQL 파일에서 아이템 ID와 카테고리 정보 추출
def parse_sql_file(sql_path):
    """SQL 파일에서 아이템 ID와 카테고리 ID 매핑 생성"""
    item_category_map = {}
    
    with open(sql_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # INSERT 문에서 아이템 정보 추출
    # INSERT INTO `items` (`item_name`,`wiki_url`,`min_progression_id`,`category_id`,`title_id`) VALUES ('Item Name', 'URL', prog_id, category_id, title_id);
    pattern = r"INSERT INTO `items`[^)]+VALUES\s*\([^,]+,\s*(?:'[^']+',\s*)?(\d+),\s*(\d+),\s*\d+\);"
    
    matches = re.finditer(pattern, content)
    item_id = 1  # 첫 번째 INSERT부터 ID 1부터 시작
    
    for match in matches:
        category_id = int(match.group(2))
        item_category_map[item_id] = category_id
        item_id += 1
    
    return item_category_map

def classify_images(items_dir, sql_path):
    """이미지 파일들을 카테고리에 따라 분류"""
    items_path = Path(items_dir)
    sql_file_path = Path(sql_path)
    
    # SQL에서 아이템-카테고리 매핑 가져오기
    item_category_map = parse_sql_file(sql_file_path)
    
    # 카테고리별 폴더 생성
    for category_id, category_name in CATEGORY_NAMES.items():
        category_dir = items_path / category_name
        category_dir.mkdir(exist_ok=True)
    
    # Item_숫자.png 파일들을 찾아서 분류
    image_files = list(items_path.glob('Item_*.png'))
    
    moved_count = 0
    not_found_count = 0
    
    for image_file in image_files:
        # Item_숫자.png에서 숫자 추출
        match = re.search(r'Item_(\d+)\.png', image_file.name)
        if not match:
            continue
        
        item_id = int(match.group(1))
        
        # 카테고리 찾기
        if item_id in item_category_map:
            category_id = item_category_map[item_id]
            category_name = CATEGORY_NAMES[category_id]
            target_dir = items_path / category_name
            
            # 파일 이동
            target_path = target_dir / image_file.name
            if not target_path.exists():
                shutil.move(str(image_file), str(target_path))
                moved_count += 1
        else:
            # 매핑이 없는 경우 others 폴더로 이동
            others_dir = items_path / 'others'
            others_dir.mkdir(exist_ok=True)
            target_path = others_dir / image_file.name
            if not target_path.exists():
                shutil.move(str(image_file), str(target_path))
                not_found_count += 1
    
    print(f"분류 완료!")
    print(f"- 이동된 파일: {moved_count}개")
    print(f"- others 폴더로 이동: {not_found_count}개")

if __name__ == '__main__':
    # 경로 설정
    base_dir = Path(__file__).parent.parent
    items_dir = base_dir / 'docs' / 'img' / 'Terraria_Items'
    sql_file = base_dir / 'migrations' / 'insert_items_list.sql'
    
    if not items_dir.exists():
        print(f"오류: {items_dir} 폴더를 찾을 수 없습니다.")
        exit(1)
    
    if not sql_file.exists():
        print(f"오류: {sql_file} 파일을 찾을 수 없습니다.")
        exit(1)
    
    classify_images(items_dir, sql_file)

