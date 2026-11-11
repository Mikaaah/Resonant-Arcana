# Spell icon palettes

Every catalogue spell resolves to one deliberate palette. Default colors follow `Spell.Theme`.
Area and named-spell overrides match existing distinctive effects, such as green Doom Beam,
purple Tremorzilla Cataclysm, red/blue magnetic poles, Helvar embers, sand, amber and candy magic.

The complete assignment is in [SPELL_ICON_COLORS.csv](SPELL_ICON_COLORS.csv).
`tools/spell-icon-map.json` retains the approved design assignment, while
`tools/spell-icon-palettes.json` owns the colors and reasons for special cases.

Original 16x16 images in `textures/gui/icon_library` remain unchanged. The builder maps their
brightness to school ramps without resizing, smoothing, cropping or changing alpha.
Each spell has its own output, so one reused design can appear as crimson Blood magic,
orange Fire magic or blue Ice magic.

Install `tools/requirements-icons.txt`, then regenerate with `python3 tools/build_spell_icons.py`.
Validate source with `python3 tools/verify_visual_assets.py`, or pass `--jar PATH` to inspect a release.
Validation checks every resolved color family, complete catalogue coverage, exact alpha masks,
deterministic output and the retained nine-book assets and recipes.

Build the complete mod through its Gradle pipeline and TNK release adapter. The 1.0.1 visual revision also corrects book lighting and spell presentation; it must use the full source build so those renderer changes are included.
