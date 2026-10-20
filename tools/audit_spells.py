"""Validate the complete catalogue and produce a reproducible release review table."""
import json,re
from collections import Counter
from pathlib import Path

ROOT=Path(__file__).resolve().parents[1]
data=json.loads((ROOT/'src/main/resources/assets/resonant_arcana/catalogue.json').read_text())
spells=data['spells'];ids=set();homes=Counter(name for school in data['schools'] for name in school['spells'])
lines=['# Catalogue audit — 0.3.0','',
       'All damage values are rank-one health points per direct hit, before equipment, defenses and server multipliers. Two health points equal one heart. Burning, poison, wither and melee contributions are additional. Multi-stage totals depend on coverage, damage immunity and the individual sequence; a beam hits each enemy once per cast.','',
       'The balance pass raises underpowered basic, sustained and heavy spells, with heavy releases bridging into the 44–72 damage range. Cinematic single hits remain 120–512. Several repeated cataclysm sequences have lower per-hit damage to account for their full release. Utility and support spells retain their roles.','',
       'Flamethrower now delivers six pulses over three seconds. Arcane Volley launches three bolts half a second apart. Both respect normal hurt immunity. These timings allow every advertised pulse to land against a stationary unprotected target without bypassing vanilla immunity.','',
       '| Spell | Category | School | Damage / hit | Pulses¹ | Mana | Cast (s) | Cooldown (s) |',
       '| --- | --- | --- | ---: | ---: | ---: | ---: | ---: |']
for name,s in spells.items():
    assert homes[name]==1,(name,'school membership')
    assert s['bookId'] not in ids,(name,'duplicate ID');ids.add(s['bookId'])
    assert 0<=s['damage']<=(2048 if s.get('tier')=='GOD' else 512) and 0<s['mana']<=(1200 if s.get('tier')=='GOD' else 500),(name,'bounds')
    assert '\n' not in s['description'] and s['description'].endswith('.'),(name,'description')
    assert len(s['description'])<500,(name,'overlong description')
    assert (ROOT/f"src/main/resources/assets/resonant_arcana/textures/gui/spell_icons/{s['bookId']}.png").is_file(),(name,'missing icon')
    school=next(x['title'] for x in data['schools'] if name in x['spells'])
    lines.append(f"| {s['title']} | {s['category'].replace('_',' ').title()} | {school} | {s['damage']:g} | {s['pulses']} | {s['mana']} | {s['castTicks']/20:g} | {s['cooldown']/20:g} |")
lines+=['','¹ Catalogue pulse count is not a guaranteed hit count. Cataclysm sequences, reactive weapon enchantments and projectile fans are described in [the spell reference](SPELLS.md).','',
        'Verification includes native casting and cancellation, rank scaling, team/PvP protections, obstruction, finite lifetimes, spell-registry tags, searchable scroll keywords, Void Domain boundaries and effects, and ordinary damage immunity for the corrected multi-hit spells. Client rendering and sound still require in-game playtesting.','']
(ROOT/'CATALOGUE_AUDIT.md').write_text('\n'.join(lines))
print(f'Audited {len(spells)} spells: unique IDs, schools, bounded values, descriptions and icons.')
