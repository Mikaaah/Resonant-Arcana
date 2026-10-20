"""Author original ritual sound beds and impacts; NumPy/SciPy and ffmpeg required.
No third-party recordings or voices. Deterministic mono 44.1 kHz Vorbis.
"""
from pathlib import Path
import json, subprocess, tempfile
import numpy as np
from scipy.signal import butter,sosfilt
from scipy.io.wavfile import write
ROOT=Path(__file__).resolve().parents[1];FOLDER=ROOT/'src/main/resources/assets/resonant_arcana';SR=44100
rng=np.random.default_rng(30021)
def band(x,lo,hi):return sosfilt(butter(3,[lo,hi],fs=SR,btype='bandpass',output='sos'),x)
def tone(t,f,rate=0,depth=0):return np.sin(2*np.pi*f*t+depth*np.sin(2*np.pi*rate*t))
def bed(name,base,bright,noise_gain):
 t=np.arange(SR*4)/SR;n=rng.standard_normal(len(t));pulse=.65+.35*np.sin(2*np.pi*t/2-.8)**2
 x=.24*tone(t,base)+.17*tone(t,base*2,1.5,1.4)+.13*tone(t,base*4,2,2)+.08*tone(t,base*6,4,.6)
 x=x*pulse+band(n,bright*.4,bright*3)*noise_gain*(.5+.5*np.sin(2*np.pi*t/4)**2)
 # Bright moving overtones make charge audible on ordinary speakers, not just a subwoofer.
 x+=.07*tone(t,bright,.5,7)*(.5+.5*np.sin(2*np.pi*t/2))
 if 'ice' in name:x+=.1*tone(t,1397,4,1)*np.exp(-5*(t%1))
 if 'nature' in name:x+=.13*tone(t,523.25,.5,1.3)
 return x
signals={
 'ritual_arcane':bed('arcane',110,660,.2),'ritual_fire':bed('fire',73.4,280,.55),
 'ritual_ice':bed('ice',174.6,1046,.12),'ritual_nature':bed('nature',130.8,392,.15),
 'ritual_abyss':bed('abyss',65.4,330,.35),'ritual_shadow':bed('shadow',82.4,440,.28),
 'doom_charge_bed':bed('doom',55,220,.36),'doom_gravity_bed':bed('gravity',41.2,165,.45)}
for name,seconds in [('doom_collapse',3.8),('orbital_detonation',5.4)]:
 t=np.arange(int(SR*seconds))/SR;n=rng.standard_normal(len(t));freq=34+100*np.exp(-t*7)
 phase=2*np.pi*np.cumsum(freq)/SR
 x=.8*np.sin(phase)*np.exp(-t*1.4)+band(n,70,9000)*np.exp(-t*1.25)*1.2
 x+=band(n,90,1800)*(.45+.1*np.sin(2*np.pi*t*7))*np.exp(-t*.65)
 x+=.13*tone(t,660,3,2)*np.exp(-t*1.6)
 signals[name]=x
sounds=json.loads((FOLDER/'sounds.json').read_text());langpath=FOLDER/'lang/en_us.json';lang=json.loads(langpath.read_text());metrics={}
with tempfile.TemporaryDirectory() as tmp:
 for name,x in signals.items():
  # A short window removes loop seams; moderate crest factor avoids clipped boom transients.
  fade=int(.025*SR);x[:fade]*=np.linspace(0,1,fade);x[-fade:]*=np.linspace(1,0,fade)
  x=np.tanh(x*1.6);x=x/max(abs(x))*.85
  wav=Path(tmp)/(name+'.wav');write(wav,SR,(x*32767).astype(np.int16))
  out=FOLDER/'sounds'/f'{name}.ogg';subprocess.run(['ffmpeg','-hide_banner','-loglevel','error','-y','-i',str(wav),'-c:a','libvorbis','-q:a','5',str(out)],check=True)
  sounds[name]={'sounds':[{'name':'resonant_arcana:'+name,'preload':True}],'subtitle':'subtitles.resonant_arcana.'+name}
  lang['subtitles.resonant_arcana.'+name]={'doom_charge_bed':'Doom gathers dark pressure','doom_gravity_bed':'Singularity tears at the air','doom_collapse':'Singularity collapses','orbital_detonation':'Orbital Ray detonates'}.get(name,'God-tier magic gathers: '+name.removeprefix('ritual_').title())
  metrics[name]={'durationSeconds':round(len(x)/SR,3),'peakDbfs':round(20*np.log10(max(abs(x))),2),'rmsDbfs':round(20*np.log10(np.sqrt(np.mean(x*x))),2)}
(FOLDER/'sounds.json').write_text(json.dumps(sounds,indent=2)+'\n');langpath.write_text(json.dumps(lang,indent=2,ensure_ascii=False)+'\n');(FOLDER/'audio/rituals.json').write_text(json.dumps({'source':'Original procedural sound design; tools/build_ritual_audio.py','sampleRate':SR,'clips':metrics},indent=2)+'\n')
print('Authored',len(signals),'original ritual audio assets.')
