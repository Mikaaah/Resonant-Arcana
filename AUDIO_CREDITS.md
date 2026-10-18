# Recorded spell audio

The project owner supplied the following MP3 recordings. Their original files
are unchanged. The mod uses normalized, mono 44.1 kHz Vorbis streams, prepared
from each complete source before splitting so the charge and release share gain.
Recordings play at their original pitch.

| Recording | Spell | Included sections |
| --- | --- | --- |
| Megumin Explosion (Sound Redesign).mp3 | Explosion | Entire decoded recording, split at 7.7 seconds |
| iamatomic.mp3 | I Am Atomic | Voice and blast through 22.5 seconds, split at 13.5 seconds; final 1.5 seconds faded |
| Cosmic Beam [Sound Design].mp3 | Arcane Devastator | Entire decoded recording, split at 6.5 seconds |
| Laser of doom sound effect.mp3 | Doom Beam | Charge excerpt 6.5–19.5 seconds; release excerpt 19.5–29 seconds |

Regenerate with `python3 tools/build_recorded_audio.py path/to/upload`.
Source checksums, section boundaries and loudness measurements are recorded in
`assets/resonant_arcana/audio/recordings.json`. The earlier bass-boosted Megumin
recording is no longer bundled in the current mod.

The repository's MIT license covers original code and assets; it does not
relicense these third-party recordings.
