"""Draw original wand sprites and generate their survival recipes (Pillow required)."""
from pathlib import Path
import json, math
from PIL import Image, ImageDraw
ROOT=Path(__file__).resolve().parents[1]
RES=ROOT/'src/main/resources'
COLOURS={'MAGIC':('#9e52e3','#e9bfff','#60417d'),'BLOOD':('#b52348','#ff8c9e','#411c2a'),
'FIRE':('#e35c1b','#ffe385','#563328'),'DARK':('#7540a5','#d3a2ed','#281f3a'),
'THUNDER':('#38a8ce','#dcffff','#3b5360'),'GRAVITY':('#b13dc0','#f6baff','#372542'),
'SOUL':('#21a899','#a4ffe4','#253c4c'),'POISON':('#6ba52f','#d8f48a','#38462d'),
'HOLY':('#d6a948','#fff6c2','#6f6043'),'ICE':('#60b6db','#e8ffff','#3c6276')}
GEMS={'MAGIC':'amethyst_shard','BLOOD':'redstone','FIRE':'blaze_powder','DARK':'ender_eye',
'THUNDER':'copper_ingot','GRAVITY':'ender_pearl','SOUL':'echo_shard','POISON':'spider_eye','HOLY':'glowstone_dust','ICE':'blue_ice'}

def write(path,data):path.parent.mkdir(parents=True,exist_ok=True);path.write_text(json.dumps(data,indent=2)+'\n')
def main():
    wands=json.loads((RES/'assets/kingdomwands/catalogue.json').read_text())['wands']
    for wand in wands:
        theme=wand['theme'];c,light,wood=COLOURS[theme]
        im=Image.new('RGBA',(64,64));d=ImageDraw.Draw(im)
        def line(points,col,width=3):d.line(points,fill=col,width=width,joint='curve')
        def crystal(x,y,r=8):
            d.polygon([(x,y-r-3),(x+r-2,y),(x,y+r),(x-r+2,y)],fill=c,outline='#171b29',width=2)
            line([(x,y-r+1),(x+2,y),(x,y+r-3)],light,2)
        # Hand-carved shaft, grip bindings and metal ferrules.
        line([(10,56),(40,26)],'#171b29',8);line([(10,54),(39,25)],wood,5)
        line([(10,53),(37,26)],'#98724e' if theme not in ('ICE','SOUL','DARK') else c,2)
        for a in (15,20,25):line([(a-2,57-a+10),(a+2,57-a+10)],'#d5bc7c',2)
        line([(32,27),(39,34)],light,3)
        if theme=='MAGIC':
            for x,y in ((38,16),(50,25),(47,9)):crystal(x,y,5)
            line([(33,28),(34,17),(40,9)],'#e4bd6c',3);line([(42,32),(54,28),(57,19)],'#e4bd6c',3)
        elif theme=='BLOOD':
            d.polygon([(43,4),(54,22),(51,31),(43,35),(36,29),(33,21)],fill=c,outline='#26141f',width=2)
            line([(43,9),(47,22),(43,29)],light,3)
            line([(32,28),(40,35),(52,34)],'#b99a69',3)
        elif theme=='FIRE':
            d.polygon([(34,31),(29,18),(39,22),(40,4),(48,18),(54,13),(53,28),(44,36)],fill=c,outline='#3c201a',width=2)
            d.polygon([(37,29),(41,16),(44,24),(49,22),(45,32)],fill=light)
        elif theme=='DARK':
            d.pieslice((29,3,59,35),35,240,fill=c);d.ellipse((41,3,62,25),fill=(0,0,0,0));crystal(40,26,6)
        elif theme=='THUNDER':
            d.polygon([(48,3),(33,23),(43,22),(34,37),(56,15),(46,17)],fill=c,outline='#172b38',width=2)
            line([(47,8),(39,20),(47,19),(40,30)],light,2)
        elif theme=='GRAVITY':
            d.ellipse((31,5,57,31),outline=c,width=4);d.arc((27,10,61,27),175,355,fill=light,width=3);crystal(44,18,7)
        elif theme=='SOUL':
            line([(36,30),(29,16),(34,7)],c,5);line([(45,34),(57,24),(57,15)],c,5)
            crystal(44,19,11);line([(32,17),(34,13)],light,2)
        elif theme=='POISON':
            line([(32,31),(35,15),(48,7),(54,15)],c,4);crystal(45,22,8)
            d.polygon([(35,24),(24,18),(29,11),(37,15)],fill=c);line([(29,14),(35,19)],light,2)
        elif theme=='HOLY':
            line([(43,6),(43,32)],c,7);line([(30,17),(55,17)],c,7)
            line([(43,7),(43,29)],light,3);line([(31,17),(54,17)],light,3)
            d.ellipse((34,6,53,27),outline='#f1d27a',width=2)
        else:
            for x,y,r in ((36,21,7),(49,21,7),(43,13,11)):crystal(x,y,r)
            line([(32,30),(44,36),(56,26)],light,3)
        folder=RES/'assets/kingdomwands/textures/item';folder.mkdir(parents=True,exist_ok=True);im.save(folder/(wand['id']+'.png'))
        write(RES/f"assets/kingdomwands/models/item/{wand['id']}.json",{'parent':'minecraft:item/handheld','textures':{'layer0':'kingdomwands:item/'+wand['id']}})
        write(RES/f"data/kingdomwands/recipe/{wand['id']}.json",{'type':'minecraft:crafting_shaped','category':'equipment','pattern':[' AG',' SA','S  '],
          'key':{'A':{'item':'irons_spellbooks:arcane_essence'},'G':{'item':'minecraft:'+GEMS[theme]},'S':{'item':'minecraft:stick'}},'result':{'id':'kingdomwands:'+wand['id'],'count':1}})
        write(RES/f"data/kingdomwands/advancement/recipes/{wand['id']}.json",{'parent':'minecraft:recipes/root','criteria':{'has_essence':{'trigger':'minecraft:inventory_changed','conditions':{'items':[{'items':'irons_spellbooks:arcane_essence'}]}},'has_the_recipe':{'trigger':'minecraft:recipe_unlocked','conditions':{'recipe':'kingdomwands:'+wand['id']}}},'requirements':[['has_essence','has_the_recipe']],'rewards':{'recipes':['kingdomwands:'+wand['id']]}})
    print(f'Generated {len(wands)} wand sprites, models, recipes and recipe unlocks.')
if __name__=='__main__':main()
