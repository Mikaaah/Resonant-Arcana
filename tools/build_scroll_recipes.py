"""Deterministic rank-one addon and God scrolls. Uses native Iron's scroll components and textures."""
import json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
data=json.loads((ROOT/'src/main/resources/assets/resonant_arcana/catalogue.json').read_text())
proofs={'magnetic_caves':'alexscaves:heart_of_iron','primordial_caves':'alexscaves:amber','toxic_caves':'alexscaves:uranium','abyssal_chasm':'alexscaves:pearl','forlorn_hollows':'alexscaves:ominous_catalyst','candy_cavity':'alexscaves:rock_candy_purple','kraken':'block_factorys_bosses:kraken_tooth','yeti':'block_factorys_bosses:ice_gauntlet','helvar':'block_factorys_bosses:bone_remains','sandworm':'block_factorys_bosses:sandworm_gauntlet','dragon':'block_factorys_bosses:dragon_bone'}
pool=['amethyst_shard','ender_pearl','blaze_powder','prismarine_crystals','echo_shard','ghast_tear','glowstone_dust','lapis_lazuli','netherite_scrap','phantom_membrane','gold_ingot','quartz','magma_cream','turtle_scute','fermented_spider_eye','diamond']
# Legacy boss entries predate integration metadata.
areas={'BOSS_TENTACLE':'kraken','BOSS_WRATH':'kraken','BOSS_ICE':'yeti','BOSS_RIFT':'helvar','BOSS_SWORD':'helvar','BOSS_SAND':'sandworm','BOSS_DRAGON':'dragon'}
signatures={};used={};doc=['# Scroll attunement recipes','','Craft these deterministic rank-one scrolls in a normal crafting grid. Core non-God spells remain craftable in Iron’s Scroll Forge. Addon and God scrolls use these recipes so their thematic materials cannot be bypassed with a generic school focus. Normal inscription and native school scroll textures remain unchanged.','','All recipes consume one paper, one ink, the listed catalyst and any listed proof. Cataclysm and God recipes also consume one nether star. God recipes additionally consume one dragon breath. A single-rank God scroll has the native enchantment glint.','','| Spell | Ink | Catalyst | Themed proof |','| --- | --- | --- | --- |'];count=0
for name,s in data['spells'].items():
 integration=s.get('integration') or ('bossesrise' if s['kind'].startswith('BOSS_') else None)
 if not integration and s.get('tier')!='GOD':continue
 area=s.get('area') or areas.get(s['kind']);index=used.get(area or s['theme'],0);used[area or s['theme']]=index+1
 catalyst='minecraft:'+pool[index%len(pool)];tier=s.get('tier');god=tier=='GOD';cat=tier=='CATACLYSM'
 if god and not integration:catalyst='minecraft:'+{'MAGIC':'amethyst_shard','THUNDER':'lightning_rod','BLOOD':'ghast_tear','FIRE':'blaze_rod','DARK':'ender_eye','ICE':'packed_ice','POISON':'flowering_azalea','HOLY':'glowstone_dust','SOUL':'echo_shard'}[s['theme']]
 ink='legendary' if god or cat else 'epic' if s['castTicks']>=40 else 'rare' if integration=='bossesrise' else 'common'
 ingredients=[{'item':'minecraft:paper'},{'item':'irons_spellbooks:'+ink+'_ink'},{'item':catalyst}]
 proof=proofs.get(area)
 overrides={'POLARITY_BOLT':'scarlet_magnet','LUX_EXTINCTION':'volcanic_core','TREMORZILLA_BREATH':'fissile_core','NUCLEAR_STRIKE':'fissile_core','HULLBREAKER_SURGE':'baleen_bone','DESOLATE_DAGGER':'desolate_dagger','PRIMORDIAL_SOVEREIGN':'tectonic_shard','TREMORZILLA_CATACLYSM':'fissile_core','ABYSSAL_WORLD_TIDE':'baleen_bone'}
 if name in overrides:proof='alexscaves:'+overrides[name]
 if proof:ingredients.append({'item':proof})
 if god or cat:ingredients.append({'item':'minecraft:nether_star'})
 if god:ingredients.append({'item':'minecraft:dragon_breath'})
 comp={'irons_spellbooks:spell_container':{'maxSpells':1,'spellWheel':False,'mustEquip':False,'data':[{'id':'resonant_arcana:'+s['bookId'],'index':0,'level':1}]}}
 if god:comp['minecraft:enchantment_glint_override']=True
 recipe={'type':'minecraft:crafting_shapeless','category':'misc','ingredients':ingredients,'result':{'id':'irons_spellbooks:scroll','count':1,'components':comp}}
 if integration:recipe['neoforge:conditions']=[{'type':'resonant_arcana:supported_integration','integration':integration}]
 signature=tuple(sorted(i['item'] for i in ingredients))
 assert signature not in signatures,(name,signatures.get(signature),'ambiguous recipe');signatures[signature]=name
 f=ROOT/f"src/main/resources/data/resonant_arcana/recipe/scrolls/{s['bookId']}.json";f.parent.mkdir(parents=True,exist_ok=True);f.write_text(json.dumps(recipe,indent=2)+'\n')
 doc.append(f"| {s['title']} | {ink.title()} | `{catalyst}` | `{proof}` |" if proof else f"| {s['title']} | {ink.title()} | `{catalyst}` | — |")
 count+=1
(ROOT/'SCROLL_RECIPES.md').write_text('\n'.join(doc)+'\n')
print('Generated',count,'deterministic scroll recipes.')
