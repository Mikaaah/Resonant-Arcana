"""Draw the original spellbook icons from the spell catalogue (Pillow required)."""
from pathlib import Path
import re,json,math,random,sys
from PIL import Image,ImageDraw,ImageFilter
ROOT=Path(__file__).resolve().parents[1]
PALETTES={'MAGIC':'#c570ff','BLOOD':'#ee365d','POISON':'#87e745','FIRE':'#ff8b32','THUNDER':'#7de8ff','DARK':'#9b6be8','GRAVITY':'#ed8aff','SOUL':'#48e9d9','HOLY':'#ffe5a1','ICE':'#a6e7ff'}

def main(only=None):
    catalogue=json.loads((ROOT/'src/main/resources/assets/resonant_arcana/catalogue.json').read_text())
    spells=[(name,entry['title'],entry['kind'],entry['theme']) for name,entry in catalogue['spells'].items()]
    folder=ROOT/'src/main/resources/assets/resonant_arcana/textures/gui/spell_icons';folder.mkdir(parents=True,exist_ok=True)
    langfile=ROOT/'src/main/resources/assets/resonant_arcana/lang/en_us.json';lang=json.loads(langfile.read_text())
    for index,(name,title,kind,theme) in enumerate(spells):
        ident=catalogue['spells'][name]['bookId']
        if only and ident not in only:continue
        im=Image.new('RGBA',(128,128));d=ImageDraw.Draw(im);c='#ab3036' if kind in ('BOSS_TENTACLE','BOSS_WRATH') else '#c49b60' if kind=='BOSS_SAND' else PALETTES[theme];white='#edfff8';rng=random.Random(index+911)
        def line(points,width=7,col=c):d.line(points,fill=col,width=width,joint='curve')
        if kind=='BOSS_TENTACLE':
            points=[(19,107),(25,82),(42,75),(70,75),(88,60),(90,40),(76,26),(57,30),(52,44),(62,50)]
            line(points,19);line(points,6,white)
            for x,y in [(32,86),(53,85),(78,77),(98,55),(91,30)]:d.ellipse((x-4,y-4,x+4,y+4),fill=white)
            d.arc((12,93,117,121),180,355,fill='#4ee9ef',width=6)
        elif kind=='BOSS_ICE':
            for x,y in [(25,45),(51,17),(79,31),(104,55)]:
                d.polygon([(x-12,108),(x,y),(x+11,108)],fill=c)
                line([(x,y+11),(x-4,99)],4,white)
        elif kind=='BOSS_SWORD':
            d.polygon([(66,115),(49,58),(64,47),(79,58)],fill='#c8cadb',outline=white)
            line([(30,50),(97,50)],9,'#51365c');line([(64,48),(64,18)],11,'#79526c')
            d.ellipse((58,8,70,24),fill=white)
            for x in (25,45,85,105):line([(x,17),(x-5,34)],4)
            d.arc((15,94,113,117),190,350,fill=c,width=5)
        elif kind=='BOSS_RIFT':
            d.ellipse((25,13,99,114),outline=c,width=10);d.ellipse((38,26,86,103),outline=white,width=4)
            line([(10,100),(42,77),(75,55),(116,25)],10);line([(50,71),(109,31)],4,white)
        elif kind=='BOSS_SAND':
            for x,y in [(30,66),(60,34),(92,59)]:
                d.polygon([(x-14,105),(x-11,y+11),(x,y),(x+12,y+11),(x+16,105)],fill=c)
                line([(x,y+14),(x-3,90)],3,white)
            d.arc((11,86,117,115),180,355,fill=white,width=4)
        elif kind=='BOSS_DRAGON':
            if name=='DRAGONS_GUARD':
                for x,y in [(27,90),(62,65),(96,40)]:
                    line([(x,y),(x+15,y-30)],10)
                    d.ellipse((x-12,y-12,x+12,y+12),fill=c)
                    d.ellipse((x-5,y-5,x+5,y+5),fill=white)
            else:
                line([(50,62),(54,13)],12);line([(70,62),(79,18)],10)
                d.ellipse((37,39,90,92),fill=c);d.ellipse((51,50,78,80),fill=white)
                d.arc((12,86,116,119),180,360,fill=white,width=6)
        elif kind=='COUNTER':
            d.polygon([(64,12),(108,32),(99,83),(64,116),(29,83),(20,32)],outline=c,width=9)
            for i in range(3):line([(36+i*18,48),(64,79),(91-i*18,48)],5,white)
        elif kind=='DRAW':
            line([(13,82),(116,38)],11);line([(18,81),(112,40)],4,white)
            for x,y in [(32,53),(76,91),(100,21)]:line([(x-6,y),(x+6,y)],4)
        elif kind=='REVERSAL':
            d.arc((15,18,113,113),40,305,fill=c,width=10);d.polygon([(15,25),(47,30),(24,53)],fill=white)
            line([(41,91),(90,40)],9)
        elif kind=='BRAND':
            d.polygon([(67,10),(31,68),(60,66),(49,119),(101,52),(72,54),(88,10)],fill=c)
            line([(25,106),(103,28)],4,white)
        elif kind=='BANNER':
            line([(31,16),(31,116)],9,white);d.polygon([(38,21),(107,21),(90,45),(108,67),(38,67)],fill=c)
            line([(57,45),(84,45)],5,white);line([(71,32),(71,58)],5,white)
        elif kind=='REBIRTH':
            for sign in (-1,1):d.polygon([(64,85),(64+sign*48,27),(64+sign*37,75),(64+sign*17,97)],fill=c)
            d.polygon([(64,15),(77,67),(64,114),(51,67)],fill=white)
        elif kind=='FORMATION':
            for x,y in [(25,51),(64,25),(101,51)]:
                d.polygon([(x,y-14),(x-7,y+21),(x,y+35),(x+7,y+21)],fill=c)
                line([(x-12,y+34),(x+12,y+34)],5,white);line([(x,y+35),(x,y+51)],6,white)
        elif kind=='BELL':
            d.pieslice((26,20,102,111),180,360,fill=c);d.rectangle((26,61,102,86),fill=c)
            d.ellipse((49,82,79,110),fill=white);line([(17,86),(112,86)],8,white)
            for x in [12,111]:line([(x,40),(x,69)],4)
        elif kind=='BREAKER':
            d.arc((19,18,110,112),35,175,fill=c,width=9);d.arc((19,18,110,112),210,345,fill=c,width=9)
            line([(20,109),(100,19)],11,white);line([(48,71),(77,94)],7)
        elif kind=='BOSS_WRATH':
            for dx in [-39,-13,13,39]:line([(64+dx,108),(64+dx*1.2,70),(64+dx*.8,36),(64+dx*.45,24)],9)
            d.ellipse((48,74,80,106),fill=white)
        elif kind=='WARCRY':
            d.arc((14,18,114,114),190,350,fill=c,width=9)
            for a in range(7):
                angle=a*math.pi/3.5;line([(64+math.cos(angle)*27,64+math.sin(angle)*27),(64+math.cos(angle)*49,64+math.sin(angle)*49)],6)
            d.polygon([(39,70),(57,37),(84,30),(77,63),(98,78),(65,85)],fill=white)
        elif kind=='SOULBLAST':
            d.ellipse((13,48,36,77),fill=white)
            for i in range(3):d.arc((24+i*19,19-i*3,63+i*19,109+i*3),285,75,fill=c,width=7)
        elif kind=='BLADE_THRUST':
            d.polygon([(21,107),(53,61),(96,17),(111,14),(109,30),(66,74)],fill=c)
            line([(31,98),(104,22)],5,white);line([(34,64),(64,93)],7)
        elif kind in ('BLINK','DASH'):
            for i in range(3):line([(18+i*23,94-i*14),(45+i*23,64-i*14),(31+i*23,43-i*14)],8)
            line([(72,99),(108,63),(83,49)],5,white)
        elif kind in ('SWEEP','EXECUTE','BLADE_ARC'):
            for i in range(3):d.arc((10+i*8,15+i*6,116-i*4,112-i*8),205,345,fill=white if i==0 else c,width=8-i)
            line([(36,91),(89,28)],6)
        elif kind=='SERPENT':
            points=[(22+i*2.4,98-i*1.7+math.sin(i*.35)*12) for i in range(35)];line(points,12)
            d.polygon([(94,20),(113,28),(94,42),(87,31)],fill=white)
        elif kind=='FAN':
            for off in (-28,0,28):line([(64,107),(64+off,25)],9);d.ellipse((58+off,19,70+off,31),fill=white)
        elif kind=='RAIN':
            for x,y in ((25,45),(55,24),(89,43),(46,86),(91,92)):line([(x+8,y-18),(x-5,y+16)],7)
            d.arc((16,82,114,120),180,355,fill=white,width=5)
        elif kind in ('BUFF','CLEANSE','REMEDY','HYMN','MARTIAL_BUFF'):
            d.polygon([(64,15),(105,35),(98,82),(64,114),(30,82),(23,35)],outline=c,width=7)
            line([(64,40),(64,85)],9,white);line([(43,62),(85,62)],9,white)
        elif kind=='TOTEM':
            line([(64,111),(64,37)],10);d.polygon([(64,13),(90,38),(64,63),(38,38)],fill=c)
            d.ellipse((54,28,74,48),fill=white);d.arc((13,79,115,118),0,360,fill=c,width=5)
        elif kind in ('PILLAR','PIERCING_ORB'):
            if kind=='PIERCING_ORB':
                for x,col in ((39,'#5b9bff'),(89,'#ff537e')):d.ellipse((x-20,44,x+20,84),fill=col)
                d.ellipse((43,43,85,85),fill=white);d.arc((12,17,116,112),195,350,fill=c,width=8)
            else:
                for x in (45,64,83):line([(x,12),(x,101)],6,white if x==64 else c)
                d.ellipse((22,91,107,114),outline=c,width=7)
        elif kind in ('TETHER','RICOCHET'):
            line([(20,99),(47,52),(80,75),(108,23)],8)
            for x,y in ((20,99),(47,52),(80,75),(108,23)):d.ellipse((x-6,y-6,x+6,y+6),fill=white)
        elif kind in ('ORBIT','DOMAIN','TRAP','GAZE','CONE_CHANNEL'):
            if kind=='GAZE':
                d.ellipse((17,43,111,88),outline=c,width=8);d.ellipse((52,47,76,83),fill=white)
            elif kind=='CONE_CHANNEL':
                for off in (-26,0,26):line([(20,105),(91+off*.4,31+off)],9)
            else:
                d.ellipse((17,30,111,98),outline=c,width=7)
                for x,y in ((26,84),(68,32),(104,81)):d.polygon([(x,y-17),(x+9,y),(x,y+17),(x-9,y)],fill=white)
        elif kind=='SONIC':
            d.ellipse((12,49,34,79),fill=white)
            for i in range(3):d.arc((20+i*16,14-i*2,72+i*16,114+i*2),285,75,fill=c,width=7)
        elif kind=='HEAL':
            d.polygon([(52,17),(77,17),(77,51),(110,51),(110,77),(77,77),(77,111),(52,111),(52,77),(18,77),(18,51),(52,51)],fill=c)
            line([(64,30),(64,98)],5,white)
        elif kind=='FANGS':
            for x in (28,64,100):d.polygon([(x-12,103),(x,22),(x+11,103)],fill=c)
        elif kind=='FROSTFRONT':
            for a in range(6):
                v=a*math.pi/3;line([(64,64),(64+math.cos(v)*48,64+math.sin(v)*48)],8)
        elif kind=='DOOM':
            d.ellipse((25,25,103,103),fill='#08040f',outline=c,width=9)
            d.arc((8,31,120,97),12,340,fill=white,width=5)
        elif kind=='METEOR':
            d.ellipse((24,55,79,110),fill=c);d.ellipse((37,68,65,96),fill=white)
            for x in (42,64,85):line([(x,63),(x+22,16)],9)
        elif kind=='RAIL':
            line([(16,111),(109,18)],13);line([(16,111),(109,18)],4,white)
            for x,y in ((42,84),(71,55)):line([(x-16,y-15),(x+14,y+15)],5)
        elif kind=='COLLAPSE':
            d.ellipse((31,31,97,97),fill='#12091c',outline=c,width=7)
            for a in (0,2.1,4.2):
                line([(64+math.cos(a)*52,64+math.sin(a)*52),(64+math.cos(a+.8)*33,64+math.sin(a+.8)*33)],8)
        elif kind in ('BOLT','COMET','SEEKER','VOLLEY','CANNON','LANCE','SONIC','CHANNEL','SIPHON'):
            for i in range(3 if kind in ('VOLLEY','CANNON') else 2):
                off=(i-1)*18
                line([(17,104+off),(54,78+off),(103,29+off)],9 if kind=='CANNON' else 6)
            d.ellipse((79,17,111,49),fill=c);d.ellipse((89,24,103,38),fill=white)
            if kind=='CANNON':
                for x,y in ((46,78),(69,55)):d.ellipse((x-8,y-8,x+8,y+8),outline=white,width=4)
        elif kind in ('WAVE','PULSE','PUSH','PULL','BURST','LIGHTNING','SMITE','CHAIN','STORM'):
            if kind in ('LIGHTNING','SMITE','CHAIN','STORM'):
                d.polygon([(75,12),(35,66),(59,65),(46,116),(100,53),(73,53)],fill=c)
                line([(75,22),(49,60),(66,59)],4,white)
            else:
                for i in range(3):d.arc((14+i*12,17+i*8,113-i*3,117-i*4),203,341,fill=c,width=7)
                line([(41,57),(65,39),(89,57)],4,white)
        elif kind in ('WARD','CAPTURE','CHOKE','SUSPEND','MARK','STUN','CONFUSE'):
            d.polygon([(64,13),(105,36),(100,81),(64,114),(28,81),(23,36)],outline=c,width=8)
            line([(64,36),(64,84)],9);d.ellipse((58,92,70,104),fill=white)
        elif kind in ('AURA','FIELD','WELL','TWISTER'):
            points=[]
            for i in range(76):
                angle=i*.22;radius=4+i*.5;points.append((64+math.cos(angle)*radius,64+math.sin(angle)*radius))
            line(points,7);d.ellipse((56,56,72,72),fill=white)
        elif kind in ('LEAP','LAUNCH'):
            line([(30,106),(64,29),(97,59)],9);line([(46,44),(64,25),(84,28)],8,white)
        else:
            d.polygon([(64,13),(89,51),(113,64),(83,84),(64,116),(43,84),(15,64),(43,43)],fill=c)
            d.ellipse((53,53,75,75),fill=white)
        for i in range(5):
            x,y=rng.randint(15,112),rng.randint(18,110);d.ellipse((x,y,x+4,y+4),fill=c)
        glow=im.filter(ImageFilter.GaussianBlur(4));im=Image.alpha_composite(glow,im)
        im.resize((32,32),Image.Resampling.LANCZOS).save(folder/(ident+'.png'))
        lang['spell.resonant_arcana.'+ident]=title
    lang['key.resonant_arcana.select']='Choose wand spell'
    lang.pop('subtitles.resonant_arcana.cast',None)
    lang.pop('subtitles.resonant_arcana.impact',None)
    langfile.write_text(json.dumps(lang,indent=2)+'\n');print('Generated',len(only) if only else len(spells),'spell icons and translations.')

if __name__=='__main__':main(set(sys.argv[1:]))
