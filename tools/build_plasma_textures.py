"""Generate original seamless plasma/ignition textures from periodic functions (standard library only)."""
from pathlib import Path
import math,struct,zlib
folder=Path(__file__).resolve().parents[1]/'src/main/resources/assets/resonant_arcana/textures/effect'
folder.mkdir(parents=True,exist_ok=True)
def chunk(tag,data):return struct.pack('>I',len(data))+tag+data+struct.pack('>I',zlib.crc32(tag+data)&0xffffffff)
for name,fire in [('plasma_noise',False),('fire_sphere',True)]:
 rows=[]
 for y in range(64):
  row=bytearray([0])
  for x in range(64):
   u=x/64*math.tau;v=y/64*math.tau
   cloud=(math.sin(u*3+math.sin(v*2))+math.sin(v*5-u*2)+math.sin(u*7+v*3)*.4)/2.4
   brightness=int(140+115*(cloud+1)/2)
   alpha=int((110+130*(cloud+1)/2) if fire else (45+180*max(0,math.sin(u*2+math.sin(v*3))))*math.sin(math.pi*x/63)**.7)
   row.extend((brightness,brightness,brightness,alpha))
  rows.append(bytes(row))
 data=b'\x89PNG\r\n\x1a\n'+chunk(b'IHDR',struct.pack('>IIBBBBB',64,64,8,6,0,0,0))+chunk(b'IDAT',zlib.compress(b''.join(rows),9))+chunk(b'IEND',b'')
 (folder/(name+'.png')).write_bytes(data)
 print('Generated',name)
