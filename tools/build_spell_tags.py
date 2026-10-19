"""Generate real Iron's spell-registry tags and the addon's equipment item tags."""
import json
from pathlib import Path

ROOT=Path(__file__).resolve().parents[1]
data=json.loads((ROOT/'src/main/resources/assets/resonant_arcana/catalogue.json').read_text())
groups={}
for name,spell in data['spells'].items():
    school=next(s['id'] for s in data['schools'] if name in s['spells'])
    extra=[]
    if spell.get('tier')=='GOD':extra.append('tiers/god')
    if spell.get('integration')=='bossesrise':extra+=['integrations/bossesrise','bosses/'+spell['area']]
    if spell.get('integration')=='alexscaves':extra+=['integrations/alexscaves','caves/'+spell['area'],'tiers/'+spell['tier'].lower()]
    for group in ['all','categories/'+spell['category'].lower(),'schools/'+school]+extra:
        entry='resonant_arcana:'+spell['bookId']
        if spell['kind'].startswith('BOSS_') or spell.get('integration'):entry={'id':entry,'required':False}
        groups.setdefault(group,[]).append(entry)
folder=ROOT/'src/main/resources/data/resonant_arcana/tags/irons_spellbooks/spells'
for name,values in groups.items():
    path=folder/(name+'.json');path.parent.mkdir(parents=True,exist_ok=True)
    path.write_text(json.dumps({'replace':False,'values':values},indent=2)+'\n')
for name,values in {'spellbooks':['runic_echoes_spell_book','phoenix_spell_book','deathchill_spell_book'],'debug_tools':['debug_wand']}.items():
    path=ROOT/f'src/main/resources/data/resonant_arcana/tags/item/{name}.json';path.parent.mkdir(parents=True,exist_ok=True)
    path.write_text(json.dumps({'replace':False,'values':['resonant_arcana:'+v for v in values]},indent=2)+'\n')
print(f'Generated {len(groups)} spell tags and 2 equipment tags.')
