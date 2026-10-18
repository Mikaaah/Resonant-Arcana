"""Generate the spell reference from the gameplay catalogue, grouped by functional category."""
from pathlib import Path
import json
ROOT=Path(__file__).resolve().parents[1]
SCHOOL={'MAGIC':'Evocation','BLOOD':'Blood','FIRE':'Fire','DARK':'Ender','GRAVITY':'Ender','THUNDER':'Lightning','SOUL':'Eldritch','POISON':'Nature','HOLY':'Holy','ICE':'Ice'}
CATEGORIES={'PROJECTILE':'Projectiles','BEAM':'Beams & Channels','AREA':'Area Magic','CONTROL':'Control','MOBILITY':'Movement','SUPPORT':'Support & Defense','SUMMON':'Summons & Constructs','MELEE':'Strikes & Drains','BOSS_ART':'Boss Arts','CATACLYSM':'Cataclysms'}
def num(n):return f'{n:g}'
def main():
 data=json.loads((ROOT/'src/main/resources/assets/resonant_arcana/catalogue.json').read_text());spells=data['spells'];bosses=sum(s['kind'].startswith('BOSS_') for s in spells.values());caves=sum(s.get('integration')=='alexscaves' for s in spells.values())
 lines=['# Spell reference','',f"**{len(spells)-bosses-caves} core spells + {bosses} optional Bosses’Rise spells + {caves} optional Alex’s Caves spells.** Every spell has one functional category and one native Iron’s school.",'',
 'Cast through an Iron’s spellbook. The three legendary books are inscribable equipment; their perks apply in the Curios spellbook slot. The debug wand exposes every enabled spell and offers category filters. It has no survival recipe.', '',
 'Values are defaults before school power, equipment, resistances and server settings. Damage and healing use health points: two health points equal one heart. Damage is per normal hit or pulse; descriptions explain final multipliers. Travel time and release sequences occur after the cast bar. Ordinary spells have up to five ranks; Doom and cataclysms have one Legendary rank.', '',
 'Offensive spells protect the caster, eligible teammates and owned pets. Healing affects the caster, owned pets and visible scoreboard teammates, plus teamless players when enabled. Cover, enemy movement, normal damage immunity and status immunities can change results. Spells do not destroy terrain. Void Exile temporarily loads its isolated cell and return chunks; ordinary spells use loaded terrain.', '',
 'Cataclysm windups use a large seal at the caster’s feet, with dense native particles spiralling down from above and a small stream into the caster. Optional AAAParticles are limited to floor seals, Explosion’s five beam seals and Doom. Without AAA, native outlines remain. Charge cues repeat throughout the cast.', '',
 '## Categories', '', '| Category | Spells |', '| --- | ---: |']
 for key,title in CATEGORIES.items():lines.append(f"| [{title}](#{key.lower()}) | {sum(s['category']==key for s in spells.values())} |")
 lines+=['','## Schools','','| School | Spells |','| --- | ---: |']
 for school in data['schools']:lines.append(f"| {school['title']} | {len(school['spells'])} |")
 for key,title in CATEGORIES.items():
  lines +=['',f'<a id="{key.lower()}"></a>',f'## {title}','']
  for ident,s in spells.items():
   if s['category']!=key:continue
   lines +=[f"### {s['title']}",'',s['description'],'',f"**{SCHOOL[s['theme']]}** · Cast **{num(s['castTicks']/20)}s** · Cooldown **{num(s['cooldown']/20)}s** · Range **{num(s['range'])} blocks** · Radius / half-width **{num(s['radius'])} blocks**.",'', '| Rank | Base damage | Base healing | Mana |','| --- | ---: | ---: | ---: |']
   for rank in range(1,s['maxLevel']+1):
    power=1+(rank-1)*data['powerPerLevel']/100;lines.append(f"| {['I','II','III','IV','V'][rank-1]} | {num(s['damage']*power) if s['damage'] else '—'} | {num(s['healing']*power) if s['healing'] else '—'} | {s['mana']+(rank-1)*s['manaStep']} |")
   lines.append('')
   if s['duration']:lines +=[f"Base release / active phase: **{num(s['duration']/20)}s**.",'']
   if ident=='WARSCREAM':lines+=['Resistance I and hostile-mob taunt last **3/4/5/6/7 seconds** at ranks I–V. Three damage pulses. No offensive damage buff or absorption shield.','']
   if ident=='SERAPH_ASCENDANT':lines+=['Flight lasts twenty seconds at every rank. Healing scales with spell power; the nearby spell-power blessing stays at +15%. Flight is removed on cleanup, with four seconds of Slow Falling. Other sources of native flight are preserved.','']
   if s['kind'] in ('BLADE_ARC','BLADE_THRUST','DRAW','REVERSAL','BREAKER'):lines+=['Weapon contribution: add half the melee attack attribute at release before spell-power scaling.','']
   if s['kind']=='CATACLYSM':lines+=['Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.','']
   if s.get('integration')=='alexscaves':lines +=[f"Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **{s['area'].replace('_',' ').title()} · {s['tier'].title()}**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.",'']
   if s['kind'].startswith('BOSS_'):lines+=['Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.','']
   lines +=[f"Spell ID: `resonant_arcana:{s['bookId']}`. Inspect ranks with `/resonantarcana spell {s['bookId']} <rank>`.",'']
 (ROOT/'SPELLS.md').write_text('\n'.join(lines))
 print('Documented',len(spells),'categorized spells.')
if __name__=='__main__':main()
