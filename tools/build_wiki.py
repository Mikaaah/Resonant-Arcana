"""Refresh documentation source pages by functional category and school."""
from pathlib import Path
import json,re,build_docs
ROOT=Path(__file__).resolve().parents[1]
def main():
 build_docs.main();data=json.loads((ROOT/'src/main/resources/assets/resonant_arcana/catalogue.json').read_text());wiki=ROOT/'wiki';wiki.mkdir(exist_ok=True)
 for p in wiki.glob('*Wand*.md'):p.unlink()
 guide=(ROOT/'SPELLS.md').read_text();sidebar=['[Home](Home.md)','[Equipment](Equipment.md)','[All Spells](../SPELLS.md)','[Configuration](Configuration.md)']
 for key,title in build_docs.CATEGORIES.items():
  body=guide.split(f'<a id="{key.lower()}"></a>',1)[1].split('<a id=',1)[0].strip();name=key.title().replace('_','-')+'.md';(wiki/name).write_text(body+'\n\n[← Home](Home.md)\n');sidebar.append(f'[{title}]({name})')
 equipment=(ROOT/'README.md').read_text().split('## Equipment',1)[1].split('## ',1)[0]
 (wiki/'Equipment.md').write_text('# Equipment\n'+equipment)
 (wiki/'_Sidebar.md').write_text('\n\n'.join(sidebar)+'\n')
 (wiki/'Home.md').write_text('# Resonant Arcana\n\nIron’s Spells ’n Spellbooks addon for NeoForge 1.21.1. Three legendary spellbooks, one debug wand, and '+str(len(data['spells']))+' spells.\n\n[Equipment](Equipment.md) · [Spell reference](../SPELLS.md) · [Configuration](Configuration.md)\n')
 for src,dst in [('CHANGELOG.md','Changelog.md'),('CONFIGURATION.md','Configuration.md')]: (wiki/dst).write_text((ROOT/src).read_text())
 for stale in ['Wand-Index.md','Spell-Mechanics.md','Controls-and-Commands.md','Getting-Started.md','Multiplayer-and-Compatibility.md','Troubleshooting.md','Developers-and-Contributors.md','Bosses-Rise-Compatibility.md']:
  p=wiki/stale
  if p.exists():p.write_text('# '+stale[:-3].replace('-',' ')+'\n\nSee the current [README](../README.md), [equipment guide](Equipment.md), [spell reference](../SPELLS.md) and [configuration](Configuration.md).\n')
 print('Refreshed category documentation.')
if __name__=='__main__':main()
