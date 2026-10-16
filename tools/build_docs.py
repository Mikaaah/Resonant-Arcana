"""Generate the public spell reference from the same catalogue consumed by the mod."""
from pathlib import Path
import json
ROOT=Path(__file__).resolve().parents[1]
SCHOOL={'MAGIC':'Evocation','BLOOD':'Blood','FIRE':'Fire','DARK':'Ender','THUNDER':'Lightning','GRAVITY':'Ender','SOUL':'Eldritch','POISON':'Nature','HOLY':'Holy','ICE':'Ice'}
SUPPORT={'WARD','CAPTURE','CHOKE','BUFF','CLEANSE','GAZE','TETHER','MARTIAL_BUFF','WARCRY'}
def number(n):return f'{n:g}'
def main():
    data=json.loads((ROOT/'src/main/resources/assets/resonant_arcana/catalogue.json').read_text());spells=data['spells']
    lines=['# Spell reference','',f"**128 core spells + 3 optional Bosses’Rise spells · 10 wands · 12–14 spells per wand.**",'',
    'Values below are defaults before armour, resistance, school power, equipment and configuration. Damage and healing use **health points**: 2 points equal one heart. A listed damage value is per successful hit or pulse, rather than guaranteed total damage. Normal Minecraft damage immunity and status-effect immunities still apply.', '',
    'All spells have five levels except **DOOM**, which has one Legendary level. Base power is 100%, 125%, 150%, 175%, and 200%. Mana increases by the spell’s listed step for each level above I. Higher levels do not change the base cast time or cooldown. Iron’s cast-speed, cooldown and school modifiers still apply.', '',
    'Craft a wand to access its level-I kit. To upgrade an individual spell, hold a matching higher-level Iron’s scroll in your offhand and sneak-use the wand. The scroll is consumed. Native Iron’s spellbooks also accept all spells through normal inscription.', '',
    'Healing affects the caster, owned pets and scoreboard teammates. By default it also affects players who, like the caster, have no scoreboard team; servers can disable this. Healing requires line of sight and cannot exceed maximum health. Offensive spells respect server PvP and scoreboard friendly-fire settings, and protect the caster’s own pets. No spell edits terrain or intentionally loads new chunks.', '',
    "## Optional Bosses’Rise spells",'',
    "Bosses’Rise **2.1.2** adds Kraken’s Grasp (Venom / Nature), Ice Spike Eruption (Frost / Ice), and Helvar’s Rift (Dark / Ender). Without that mod, the three spells are absent from registries, scrolls, commands and wand selections. Existing spell IDs remain unchanged. Other Bosses’Rise versions leave this adapter disabled until validated.", '',
    "Visual-only entities reference the installed mod’s models, animations and textures. They do not run original boss attack ticks, persist in saves, break terrain, drop loot or qualify as spell targets. Damage and status effects use Resonant Arcana’s targeting, line-of-sight, PvP and friendly-fire rules. Sounds also come from the installed mod; no third-party assets are bundled.", '',
    "Kraken’s Grasp has a 1.2-second cast bar, followed by approximately 2.3 seconds of rise/attack animation before one slam; its visual fades afterward. Ice Spike Eruption has a 0.8-second cast bar, then five eruptions over 0.75–1.35 seconds; each enemy can take one hit per cast. Helvar’s Rift has a 0.9-second cast bar, then travels at 1.2 blocks per tick up to 28 blocks, stopping at walls or its first enemy. Ground spells need a visible solid surface within a bounded ground search; they cannot place attacks through ceilings or walls.", '',
    '## Wand index' ,'', '| Wand | School | Spell kit |','| --- | --- | --- |']
    for w in data['wands']:
        lines.append(f"| [{w['title']}](#{w['id'].replace('_','-')}) | {SCHOOL[w['theme']]} | "+', '.join(spells[n]['title'] for n in w['spells'])+' |')
    lines+=['','## Shared recovery','',
    'Healing and cleansing spells share a recovery group. Blood Ward, Echo Ward and Dawn Ward share another; stuns, restraints and life drains each share their own group. Swapping wands or switching to a spellbook does not bypass the recovery already applied. Other cooldowns are per spell and player, rather than per item.', '',
    '## Secondary effects and level scaling','',
    '- Fire hits ignite for three seconds. Nature hits poison for four seconds. Dark shadow hits apply Weakness I for three seconds; Nightfall also applies Darkness.',
    '- Frost hits apply Slowness I for two seconds and add 40 freezing ticks, capped at 240. Freeze buildup is not a guaranteed stun and can decay or be resisted normally. Absolute Zero additionally imposes Slowness IV and at least 160 freezing ticks.',
    '- Ward, restraint, tether, blessing, cleansing-speed and gaze durations scale with spell power, capped at twice their base duration. Resistance and Strength amplifiers remain fixed. Absorption scales with spell power, capped at 16 health, and refreshes rather than adding unlimited shields.',
    '- Blink range gains up to four blocks from spell power, reaching the extra four at level V without gear. The destination must be clear, loaded, and outside lava. Blinks follow the caster’s line of sight; they do not search for a safe floor, so aiming into open air still requires care.',
    '- Siphons restore 40% of health actually removed, capped at 2 health times spell power, before the healing multiplier. They provide no healing when damage is blocked. Projectile fans and volleys list damage per projectile; normal hit immunity prevents treating all overlapping projectiles as guaranteed combined damage.', '',
    '## Heavy cast timing','',
    'Windup is the native Iron’s cast bar. Some spells also have a release phase: Solar Cataclysm warns for 0.6 seconds before its meteor falls; Event Horizon pulls for 1.5 seconds; DOOM grows and devours its core before collapsing at 4.5 seconds. Gravity Stamp and Seraphic Descent mark their impact for one second after the cast bar. Other attacks may take additional time to travel. Interrupting the cast bar cancels the release; already-released fields continue for their stated lifetime.', '']
    for w in data['wands']:
        lines +=[f"<a id=\"{w['id'].replace('_','-')}\"></a>",f"## {w['title']}",'',f"**{SCHOOL[w['theme']]} school.** Saved item ID: `resonant_arcana:{w['id']}`.",'']
        for key in w['spells']:
            s=spells[key];lines +=[f"### {s['title']}",'',s['description'],'',
            f"**Role:** {s['role']} · **Cast:** {number(s['castTicks']/20)+'s' if s['castTicks'] else 'instant'} · **Cooldown:** {number(s['cooldown']/20)}s · **Range:** {number(s['range'])} blocks · **Radius / width:** {number(s['radius'])} blocks.",'',
            '| Level | Direct damage | Healing | Mana |','| --- | ---: | ---: | ---: |']
            for rank in range(1,s['maxLevel']+1):
                power=1+(rank-1)*data['powerPerLevel']/100
                lines.append(f"| {['I','II','III','IV','V'][rank-1]} | {number(s['damage']*power) if s['damage'] else '—'} | {number(s['healing']*power) if s['healing'] else '—'} | {s['mana']+(rank-1)*s['manaStep']} |")
            lines.append('')
            if s['pulses']>1:lines +=[f"**Delivery:** up to {s['pulses']} {'projectiles' if s['kind'] in ('FAN','VOLLEY') else 'hits / pulses'}; the table gives the value for each one.",'']
            if s['duration']>0:
                text=f"**Base active phase:** {number(s['duration']/20)}s."
                if s['kind'] in SUPPORT:text+=' Duration at levels I–V without gear: '+', '.join(number(s['duration']/20*(1+.25*i))+'s' for i in range(s['maxLevel']))+'.'
                lines +=[text,'']
            if key in ('SANGUINE_PACT','BLESSING_OF_VIGOR','RESONANT_GUARD','FROZEN_AEGIS'):
                shield=4 if key in ('SANGUINE_PACT','BLESSING_OF_VIGOR') else 6
                lines +=['**Absorption at levels I–V:** '+', '.join(number(shield*(1+.25*i)) for i in range(5))+' health.','']
            if s['kind'] in ('BLADE_ARC','BLADE_THRUST'):
                lines +=['**Weapon contribution:** add 50%, 62.5%, 75%, 87.5%, or 100% of the caster’s melee attack attribute at release to the listed base damage, at levels I–V respectively. This uses the equipped weapon’s attributes; the spell is not an automatic melee swing. Cast from an Iron’s spellbook while holding your weapon to use its attack damage.','']
            if key=='WARSCREAM':lines +=['**Ally damage:** +10%, +15%, +20%, +25%, +30% melee damage and native Iron’s spell power. **Temporary health:** 4, 5, 6, 7, 8. Taunts at most 32 visible hostile mobs, rechecking every half second for three seconds. Custom boss AI may override the target. Does not taunt animals, pets or players.','']
            if key=='BULWARK_CHANT':lines +=['**Defence:** Resistance I and 4, 5, 6, 7, 8 temporary health.','']
            if key=='ARCANE_EDGE':lines +=['**Attack damage:** +2, +2.5, +3, +3.5, +4. **Attack speed:** +10%, +12.5%, +15%, +17.5%, +20%.','']
            if key=='BATTLE_TEMPO':lines +=['**Attack and movement speed:** +10%, +12.5%, +15%, +17.5%, +20%.','']
            if key=='BLOOD_REND':lines +=['**Drain:** 40% of health actually removed, capped at 3, 3.75, 4.5, 5.25, 6 health per entire cast, before the healing configuration multiplier. Blocked damage gives no healing.','']
            if key=='SOULBLAST':lines +=['**Aim modes:** while gliding, boost along your look direction (initial contribution 1.7–2.1 blocks/tick plus 30% of existing motion, final speed capped at 3). Otherwise, look down at least 40.6 degrees to launch upward at 1.05–1.45 blocks/tick and gain three seconds of Slow Falling. Forward casts hit a 120-degree cone and apply horizontal knockback, respecting knockback resistance. Mobility modes deal no damage and do not force Elytra flight. Slow Falling is brief, not permanent fall immunity.','']
            if s['kind']=='EXECUTE':lines +=['**Conditional direct damage at levels I–V:** '+', '.join(number(s['damage']*1.5*(1+.25*i)) for i in range(5))+'.','']
            lines +=[f"Spell ID: `resonant_arcana:{s['bookId']}`. Inspect any rank in game with `/resonantarcana spell {s['bookId']} <level>`.",'']
    (ROOT/'SPELLS.md').write_text('\n'.join(lines))
    print(f"Documented {len(spells)} spells from catalogue.json.")
if __name__=='__main__':main()
