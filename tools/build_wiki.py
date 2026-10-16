"""Refresh generated wiki pages from the catalogue and public guides."""
import json
import re
from pathlib import Path
import build_docs

ROOT = Path(__file__).resolve().parents[1]


def main():
    build_docs.main()
    data = json.loads((ROOT / 'src/main/resources/assets/resonant_arcana/catalogue.json').read_text())
    spells = data['spells']
    optional = sum(s['kind'].startswith('BOSS_') for s in spells.values())
    core = len(spells) - optional
    version = re.search(r"version = '([^']+)'", (ROOT / 'build.gradle').read_text())[1]
    wiki = ROOT / 'wiki'
    wiki.mkdir(exist_ok=True)
    guide = (ROOT / 'SPELLS.md').read_text()
    footer = '\n\n[← Wiki Home](Home.md)\n'
    for anchor, body in re.findall(r'<a id="([^"]+)"></a>\n(.*?)(?=<a id=|\Z)', guide, re.S):
        filename = anchor.title() + '.md'
        (wiki / filename).write_text(body.replace('## ', '# ', 1).strip() + footer)
    lines = ['# Wands & Spell Index', '',
             f'**{core} core spells + {optional} optional Bosses’Rise spells**, organized into ten wands.', '',
             '| Wand | School | Spells with Bosses’Rise |', '| --- | --- | ---: |']
    for wand in data['wands']:
        page = wand['title'].replace(' ', '-') + '.md'
        lines.append(f"| [{wand['title']}]({page}) | {build_docs.SCHOOL[wand['theme']]} | {len(wand['spells'])} |")
    lines += ['', 'Optional spells appear only with Bosses’Rise 2.1.2. See [Bosses’Rise Compatibility](Bosses-Rise-Compatibility.md) and the [full spell reference](../SPELLS.md).']
    (wiki / 'Wand-Index.md').write_text('\n'.join(lines) + footer)
    optional_text = guide.split('## Optional Bosses’Rise spells', 1)[1].split('## Wand index', 1)[0]
    boss_table = '''
| Boss | Spells | Wand |
| --- | --- | --- |
| Kraken | Grasp, Wrath, Undertow | [Venom](Venom-Wand.md) |
| Yeti | Ice Spike Eruption, Glacial Wall | [Frost](Frost-Wand.md) |
| Underworld Knight / Helvar | Rift, Fracture, Swordfall | [Dark](Dark-Wand.md) |
| Sandworm | Eruption, Quicksand | [Rift](Rift-Wand.md) |
| Infernal Dragon | Guard, Pyre | [Cinder](Cinder-Wand.md) |

Helvar’s Swordfall displays the installed mod’s `block_factorys_bosses:knight_sword` item. After the cast bar, the sword falls for 1.2 seconds before one area impact. It does not drop an obtainable item or alter blocks.
'''
    (wiki / 'Bosses-Rise-Compatibility.md').write_text('# Optional Bosses’Rise Integration\n' + optional_text + boss_table + footer)
    preface = guide.split('## Optional Bosses’Rise spells', 1)[0].replace('# Spell reference', '# Spell Mechanics', 1)
    mechanics = guide.split('## Shared recovery', 1)[1].split('<a id=', 1)[0]
    (wiki / 'Spell-Mechanics.md').write_text(preface + '## Shared recovery' + mechanics + footer)
    for source, page in [('CONFIGURATION.md', 'Configuration.md'), ('CHANGELOG.md', 'Changelog.md')]:
        (wiki / page).write_text((ROOT / source).read_text().rstrip() + footer)
    home = wiki / 'Home.md'
    if home.exists():
        text = re.sub(r'Current documented release: [^*]+', f'Current documented release: {version}', home.read_text())
        text = re.sub(r'137 core spells \+ \d+ optional Bosses’Rise spells', f'{core} core spells + {optional} optional Bosses’Rise spells', text)
        home.write_text(text)
    for page in wiki.glob('*.md'):
        text = page.read_text()
        for filename in ['SPELLS.md', 'README.md', 'CONTRIBUTING.md', 'LICENSE']:
            text = text.replace('](' + filename, '](../' + filename)
        text = text.replace('](CONFIGURATION.md', '](Configuration.md')
        text = text.replace('**AAAParticles is optional.** Installing it adds 222 packaged native effects; Iron’s particles and vanilla trails remain available without it. The addon includes no native renderer or extra particle-mod dependency.',
                            '**AAAParticles is optional and limited to 24 spell-circle effects.** Other visuals use vanilla, Iron’s or Bosses’Rise particles and entities. Bosses’Rise spells do not use AAAParticles.')
        text = text.replace('The build also validates the texture paths of all 222 packaged effects.',
                            f'Those check counts belong to the previously published build. {version} passes Java 21 compilation and Gradle validation of 24 packaged effects; fresh dedicated-server checks remain pending.')
        page.write_text(text)
    print(f'Refreshed wiki for {version}: {len(spells)} spells.')


if __name__ == '__main__':
    main()
