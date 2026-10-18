"""Prepare owner-supplied spell recordings, without altering the MP3 originals.
Usage: python3 tools/build_recorded_audio.py path/to/upload
Requires FFmpeg. Normalize each complete source before splitting so its parts
share gain. Atomic keeps its voice and initial blast, with a shortened, faded release tail.
"""
from pathlib import Path
import subprocess,json,hashlib,sys,tempfile
ROOT=Path(__file__).resolve().parents[1];assets=ROOT/'src/main/resources/assets/resonant_arcana';out=assets/'sounds';out.mkdir(exist_ok=True)
SOURCES=[('explosion','Megumin Explosion (Sound Redesign).mp3',0,7.7,None),('atomic','iamatomic.mp3',0,13.5,22.5),('cosmic','Cosmic Beam [Sound Design].mp3',0,6.5,None),('doom_laser','Laser of doom sound effect.mp3',6.5,19.5,29.0)]
metadata=[]
for prefix,name,start,split,end in SOURCES:
 source=Path(sys.argv[1])/name
 probe=subprocess.run(['ffmpeg','-hide_banner','-i',str(source),'-af','loudnorm=I=-18:TP=-2:LRA=11:print_format=json','-f','null','-'],capture_output=True,text=True,check=True)
 stats=json.loads(probe.stderr[probe.stderr.rfind('{'):])
 norm='loudnorm=I=-18:TP=-2:LRA=11:linear=true:'+':'.join(f'{key}={stats[val]}' for key,val in [('measured_I','input_i'),('measured_TP','input_tp'),('measured_LRA','input_lra'),('measured_thresh','input_thresh'),('offset','target_offset')])
 with tempfile.TemporaryDirectory() as temp:
  pcm=Path(temp)/'normalized.flac'
  subprocess.run(['ffmpeg','-v','error','-y','-i',str(source),'-af',norm,'-ac','1','-ar','44100',str(pcm)],check=True)
  for phase,a,b in [('charge',start,split),('release',split,end)]:
   trim=['-ss',str(a)]+(['-t',str(b-a)] if b is not None else [])
   subprocess.run(['ffmpeg','-v','error','-y','-i',str(pcm),*trim,*(['-af','afade=t=out:st=7.5:d=1.5'] if prefix=='atomic' and phase=='release' else []),'-c:a','libvorbis','-q:a','6',str(out/f'{prefix}_{phase}.ogg')],check=True)
 metadata.append({'prefix':prefix,'source':name,'sourceSha256':hashlib.sha256(source.read_bytes()).hexdigest(),'startSeconds':start,'splitSeconds':split,'endSeconds':end,'chargeTicks':round((split-start)*20),'normalization':stats,'completeRecording':start==0 and end is None})
 if prefix=='atomic':metadata[-1]['releaseFadeSeconds']=1.5
 if prefix=='doom_laser':metadata[-1]['assignedSpell']='DOOM_BEAM'
 print('Prepared',prefix,flush=True)
(assets/'audio/recordings.json').write_text(json.dumps(metadata,indent=2)+'\n')
for old in ['explosion_chant.ogg','explosion_blast.ogg']:(out/old).unlink(missing_ok=True)
(assets/'audio/explosion.json').unlink(missing_ok=True)
