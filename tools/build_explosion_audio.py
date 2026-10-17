"""Convert the owner-supplied chant/boom recording into two synchronized OGG streams.
Usage: python3 tools/build_explosion_audio.py path/to/recording.mp3
The source file is never modified. Requires FFmpeg on PATH.
"""
from pathlib import Path
import hashlib,json,subprocess,sys
ROOT=Path(__file__).resolve().parents[1]
source=Path(sys.argv[1]);assets=ROOT/'src/main/resources/assets/resonant_arcana';folder=assets/'sounds';folder.mkdir(exist_ok=True)
for name,trim in [('explosion_chant',['-t','20.95']),('explosion_blast',['-ss','20.95'])]:
    subprocess.run(['ffmpeg','-v','error','-y','-i',str(source),*trim,'-ac','1','-ar','44100','-af','volume=0.8','-c:a','libvorbis','-q:a','5',str(folder/(name+'.ogg'))],check=True)
info={'source':'Megumin Bass Boosted Explosion.mp3','reference':'https://www.youtube.com/watch?v=P8QA375KWv0','sourceSha256':hashlib.sha256(source.read_bytes()).hexdigest(),'sourceDurationSeconds':27.742041,'splitSeconds':20.95,'baseCastTicks':420,'gain':0.8,'parts':['sounds/explosion_chant.ogg','sounds/explosion_blast.ogg']}
metadata=assets/'audio/explosion.json';metadata.parent.mkdir(exist_ok=True);metadata.write_text(json.dumps(info,indent=2)+'\n')
print('Converted full recording into chant and blast streams.')
