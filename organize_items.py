import os
import re
import shutil
from pathlib import Path

# 경로 설정
sql_file = 'migrations/insert_items_list.sql'
items_dir = Path('docs/img/Terraria_Items')
base_dir = Path('docs/img/Terraria_Items')

# 카테고리 매핑
category_map = {
    1: 'Melee',
    2: 'Ranged',
    3: 'Magic',
    4: 'Summon'
}

def extract_item_categories(sql_file_path):
    """SQL 파일에서 아이템 ID와 카테고리 매핑 추출"""
    item_categories = {}
    
    with open(sql_file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    item_id = 1  # AUTO_INCREMENT이므로 1부터 시작
    for line in lines:
        if line.strip().startswith('INSERT INTO'):
            # category_id 추출
            match = re.search(r"category_id`,`title_id`\) VALUES \([^,]+,\s*(\d+),\s*\d+", line)
            if match:
                category_id = int(match.group(1))
                item_categories[item_id] = category_id
                item_id += 1
    
    return item_categories

def organize_images():
    """이미지 파일을 카테고리별로 분류"""
    # 카테고리 폴더 생성
    for category_name in category_map.values():
        category_dir = base_dir / category_name
        category_dir.mkdir(exist_ok=True)
    
    # 아이템 카테고리 매핑 추출
    item_categories = extract_item_categories(sql_file)
    
    print(f"Total items found in SQL: {len(item_categories)}")
    
    # 이미지 파일 이동
    moved_count = 0
    not_found_count = 0
    
    for item_id, category_id in item_categories.items():
        image_file = base_dir / f"Item_{item_id}.png"
        
        if image_file.exists():
            category_name = category_map.get(category_id, 'Unknown')
            target_dir = base_dir / category_name
            target_path = target_dir / image_file.name
            
            shutil.move(str(image_file), str(target_path))
            moved_count += 1
        else:
            not_found_count += 1
            if not_found_count <= 10:  # 처음 10개만 출력
                print(f"Image not found: {image_file.name}")
    
    print(f"\nMoved {moved_count} images")
    print(f"Not found: {not_found_count} images")
    
    # 카테고리별 파일 개수 출력
    print("\nFiles per category:")
    for category_name in category_map.values():
        category_dir = base_dir / category_name
        if category_dir.exists():
            count = len(list(category_dir.glob("*.png")))
            print(f"  {category_name}: {count} files")

if __name__ == "__main__":
    organize_images()




