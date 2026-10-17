"""Derive Explosion's crimson/amber seal from the existing authored fire seal.
Only packed RGB values in Effekseer's BIN_ runtime chunk change; layout, textures,
node counts and chunk lengths stay identical. Run with Python 3, no editor needed.
"""
from pathlib import Path
import struct
ROOT=Path(__file__).resolve().parents[1]
folder=ROOT/'src/main/resources/assets/resonant_arcana/effeks'
b=bytearray((folder/'ward_fire.efkefc').read_bytes());offset=8;changed=False
while offset<len(b):
    tag=b[offset:offset+4];size=struct.unpack_from('<I',b,offset+4)[0];start=offset+8
    if tag==b'BIN_':
        runtime=bytes(b[start:start+size])
        for old,new,count in [('ff6515','f30523',14),('ffd35c','ff9819',7)]:
            old,new=bytes.fromhex(old),bytes.fromhex(new)
            assert runtime.count(old)==count,'Fire seal source changed; review palette mapping'
            runtime=runtime.replace(old,new)
        b[start:start+size]=runtime;changed=True
    offset=start+size
assert changed and offset==len(b)
(folder/'explosion_seal.efkefc').write_bytes(b)
print('Generated crimson/amber Explosion seal.')
