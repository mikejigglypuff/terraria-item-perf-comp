import pandas as pd
import re
import sys

# Excel 파일 읽기
excel_file = 'Terraria_weapons_with_links.xlsx'
sql_file = 'insert_items_list.sql'

try:
    # Excel 파일 읽기 (A열: item_name, B열: wiki_url 가정)
    df = pd.read_excel(excel_file)
    
    # item_name과 wiki_url 매핑 생성
    wiki_url_map = {}
    for _, row in df.iterrows():
        item_name = str(row.iloc[0]).strip() if pd.notna(row.iloc[0]) else None
        wiki_url = str(row.iloc[1]).strip() if pd.notna(row.iloc[1]) else None
        
        if item_name:
            wiki_url_map[item_name] = wiki_url if wiki_url and wiki_url != 'nan' else None
    
    # SQL 파일 읽기
    with open(sql_file, 'r', encoding='utf-8') as f:
        sql_content = f.read()
    
    # INSERT 문 패턴 매칭 및 업데이트
    def update_insert_line(line):
        # INSERT INTO `items` (`item_name`,...) VALUES ('Item Name', ...);
        match = re.match(r"(INSERT INTO `items` \(`item_name`,`min_progression_id`,`category_id`,`title_id`\) VALUES \(')([^']+)(')(, )(\d+)(, )(\d+)(, )(\d+)(\);)", line.strip())
        if match:
            prefix = match.group(1)
            item_name = match.group(2)
            quote = match.group(3)
            sep1 = match.group(4)
            prog_id = match.group(5)
            sep2 = match.group(6)
            cat_id = match.group(7)
            sep3 = match.group(8)
            title_id = match.group(9)
            suffix = match.group(10)
            
            # wiki_url 찾기
            wiki_url = wiki_url_map.get(item_name)
            wiki_url_value = f"'{wiki_url.replace(chr(39), chr(39)+chr(39))}'" if wiki_url else 'NULL'
            
            # 새로운 INSERT 문 생성 (wiki_url 컬럼 추가)
            new_line = f"INSERT INTO `items` (`item_name`,`img_url`,`wiki_url`,`min_progression_id`,`category_id`,`title_id`) VALUES ('{item_name}', NULL, {wiki_url_value}, {prog_id}, {cat_id}, {title_id});"
            return new_line
        return line
    
    # 각 줄 업데이트
    lines = sql_content.split('\n')
    updated_lines = []
    for line in lines:
        if line.strip().startswith('INSERT INTO `items`'):
            updated_line = update_insert_line(line)
            updated_lines.append(updated_line)
        else:
            updated_lines.append(line)
    
    # 업데이트된 SQL 파일 쓰기
    with open(sql_file, 'w', encoding='utf-8') as f:
        f.write('\n'.join(updated_lines))
    
    print(f"Successfully updated {sql_file} with wiki_url from {excel_file}")
    print(f"Total items in Excel: {len(wiki_url_map)}")
    
except Exception as e:
    print(f"Error: {e}", file=sys.stderr)
    import traceback
    traceback.print_exc()
    sys.exit(1)


