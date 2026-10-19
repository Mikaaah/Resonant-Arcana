"""Refresh spell category references; preserve curated wiki guides and navigation."""
from pathlib import Path
import build_docs
ROOT=Path(__file__).resolve().parents[1]
def main():
 build_docs.main();guide=(ROOT/'SPELLS.md').read_text();wiki=ROOT/'wiki';wiki.mkdir(exist_ok=True)
 for key,title in build_docs.CATEGORIES.items():
  body=guide.split(f'<a id="{key.lower()}"></a>',1)[1].split('<a id=',1)[0].strip()
  (wiki/(key.title().replace('_','-')+'.md')).write_text(body+'\n\n[← Home](Home)\n')
 for src,dst in [('CHANGELOG.md','Changelog.md'),('CONFIGURATION.md','Configuration.md'),('SCROLL_RECIPES.md','Scroll-Recipes.md')]:
  (wiki/dst).write_text((ROOT/src).read_text())
 print('Updated spell references; curated player guides retained.')
if __name__=='__main__':main()
