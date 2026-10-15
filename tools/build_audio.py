"""Generate original short spell whooshes and impacts (Python, numpy and ffmpeg)."""
from pathlib import Path
import json, math, subprocess, tempfile, wave
import numpy as np

ROOT=Path(__file__).resolve().parents[1]/'src/main/resources/assets/kingdomwands'
THEMES=('magic','blood','poison','fire','thunder','dark','gravity','soul')

def sound(theme,impact):
    index=THEMES.index(theme);rate=24000;duration=(.65 if impact else .38)+(index%3)*.06
    t=np.arange(int(rate*duration))/rate;u=t/duration;rng=np.random.default_rng(719+index*23+impact)
    noise=rng.normal(0,.7,len(t));smooth=np.convolve(noise,np.ones(7+index%3*4)/(7+index%3*4),mode='same')
    if impact:
        # A fast attack, downward body and bright transient, with a short resonant tail.
        env=np.minimum(t/.004,1)*np.exp(-t*8)
        phase=2*math.pi*(48*t+110*(1-np.exp(-t*18))/18)
        body=np.sin(phase)*np.exp(-t*10)
        crack=noise*np.exp(-t*48)
        pitch=(260,190,330,115,480,95,65,210)[index]
        resonance=(np.sin(2*math.pi*pitch*t)+.4*np.sin(2*math.pi*pitch*1.503*t))*np.exp(-t*7)
        x=env*(smooth*.65+body*.7)+crack*.5+resonance*.24
        if theme=='thunder':x+=noise*np.exp(-((t-.07)/.025)**2)*.4
        if theme in ('soul','magic'):x+=np.sin(2*math.pi*(780*t-170*t*t))*np.exp(-t*6)*.12
    else:
        # A brief intake followed by an accelerating broadband sweep and sharp release.
        env=np.sin(np.pi*u)**1.5
        sweep=np.sin(2*math.pi*(140*t+1000*t*t))*np.exp(-((u-.6)/.32)**2)
        x=env*(smooth*.9+noise*.13+sweep*.2)
        x+=np.sin(2*math.pi*(65*t+85*t*t))*np.exp(-((u-.8)/.12)**2)*.25
        if theme in ('dark','gravity'):x+=np.sin(2*math.pi*45*t)*env*.25
    # Prevent clipping without erasing the attack.
    x=np.tanh(x*1.25);x/=max(1,float(np.max(np.abs(x)))/.92)
    return (x*32767).astype('<i2').tobytes(),rate

def main():
    folder=ROOT/'sounds';folder.mkdir(parents=True,exist_ok=True);entries={}
    with tempfile.TemporaryDirectory() as temp:
        for theme in THEMES:
            for impact in (False,True):
                name=('impact_' if impact else 'cast_')+theme;data,rate=sound(theme,impact)
                source=Path(temp)/(name+'.wav')
                with wave.open(str(source),'wb') as f:f.setnchannels(1);f.setsampwidth(2);f.setframerate(rate);f.writeframes(data)
                subprocess.run(['ffmpeg','-y','-loglevel','error','-i',str(source),'-c:a','libvorbis','-q:a','4',str(folder/(name+'.ogg'))],check=True)
                entries[name]={'subtitle':'subtitles.kingdomwands.'+('impact' if impact else 'cast'),'sounds':[{'name':'kingdomwands:'+name,'attenuation_distance':24 if impact else 16}]}
    (ROOT/'sounds.json').write_text(json.dumps(entries,indent=2)+'\n')
    print('Generated 16 original spell audio samples.')

if __name__=='__main__':main()
