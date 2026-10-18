package nl.townsandkingdoms.resonantarcana;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

/** Twelve direct choices, with rank and full descriptions; never performs client-side casting. */
final class WandSelectScreen extends Screen {
    private final WandItem wand;
    private final ItemStack stack;
    private Spell hovered;
    private int page;
    private int category=-1;
    private String query="";
    private net.minecraft.client.gui.components.EditBox search;
    private boolean searchFocused;
    private java.util.List<Spell> filtered;
    private int left,top,panelWidth,cardHeight,gridBottom;
    WandSelectScreen(WandItem wand,ItemStack stack){super(Component.literal("Debug Spell Archive"));this.wand=wand;this.stack=stack;this.page=Math.max(0,SpellIndex.ordered().indexOf(wand.selected(stack)))/12;}
    @Override protected void init(){
        filtered=SpellIndex.ordered().stream().filter(wand.spells::contains).filter(s->category<0||s.category()==SpellCategory.values()[category]).filter(s->SpellIndex.matches(s,query)).toList();
        page=Math.clamp(page,0,Math.max(0,(filtered.size()-1)/12));
        panelWidth=Math.min(660,width-24);left=(width-panelWidth)/2;top=12;
        cardHeight=Math.clamp((height-170)/3,25,48);int cardWidth=(panelWidth-30)/4;
        clearWidgets();
        for(int i=0;i<12&&page*12+i<filtered.size();i++)addRenderableWidget(new Card(wand.spells.indexOf(filtered.get(page*12+i)),left+9+(i%4)*(cardWidth+4),top+58+(i/4)*(cardHeight+5),cardWidth,cardHeight));
        search=new net.minecraft.client.gui.components.EditBox(font,left+10,top+32,panelWidth/2-16,20,Component.literal("Search spells"));
        search.setMaxLength(80);search.setHint(Component.literal("Search names, schools, keywords…"));search.setValue(query);
        search.setResponder(value->{query=value;page=0;searchFocused=true;init();});addRenderableWidget(search);if(searchFocused)setFocused(search);
        addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal(category<0?"All categories":SpellCategory.values()[category].title),b->{category++;if(category>=SpellCategory.values().length)category=-1;page=0;searchFocused=false;init();}).bounds(left+panelWidth/2,top+32,panelWidth/2-10,20).build());
        if(filtered.size()>12){
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal("<"),b->turnPage(-1)).bounds(left+panelWidth-92,top+8,20,20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal(">"),b->turnPage(1)).bounds(left+panelWidth-26,top+8,20,20).build());
        }
        gridBottom=top+58+3*(cardHeight+5);
    }
    @Override public boolean isPauseScreen(){return false;}
    private void turnPage(int delta){page=Math.floorMod(page+delta,Math.max(1,(filtered.size()+11)/12));init();}
    private void choose(int slot){
        if(slot<0||slot>=wand.spells.size())return;
        if(minecraft.player!=null&&minecraft.player.getMainHandItem().getItem()==wand)
            PacketDistributor.sendToServer(new WandNetwork.Select(BuiltInRegistries.ITEM.getKey(wand),slot));
        onClose();
    }
    @Override public boolean keyPressed(int key,int scan,int modifiers){
        if(search!=null&&search.isFocused()&&key!=GLFW.GLFW_KEY_ESCAPE)return super.keyPressed(key,scan,modifiers);
        if(key==GLFW.GLFW_KEY_LEFT||key==GLFW.GLFW_KEY_PAGE_UP){turnPage(-1);return true;}
        if(key==GLFW.GLFW_KEY_RIGHT||key==GLFW.GLFW_KEY_PAGE_DOWN){turnPage(1);return true;}
        int slot=key>=GLFW.GLFW_KEY_1&&key<=GLFW.GLFW_KEY_9?key-GLFW.GLFW_KEY_1:key==GLFW.GLFW_KEY_0?9:key==GLFW.GLFW_KEY_MINUS?10:key==GLFW.GLFW_KEY_EQUAL?11:-1;
        if(slot>=0&&page*12+slot<filtered.size()){choose(wand.spells.indexOf(filtered.get(page*12+slot)));return true;}return super.keyPressed(key,scan,modifiers);
    }
    @Override public void render(GuiGraphics g,int mouseX,int mouseY,float partialTick){
        if(minecraft.player==null||minecraft.player.getMainHandItem().getItem()!=wand){onClose();return;}
        g.fillGradient(0,0,width,height,0xE8111723,0xEF080C14);
        int color=0xFF000000|wand.selected(stack).theme.color;
        g.fill(left,top,left+panelWidth,Math.min(height-8,gridBottom+88),0xE00C1220);
        g.fill(left,top,left+panelWidth,top+2,color);
        g.drawString(font,title,left+10,top+9,0xFFEAF0FC,false);
        g.drawString(font,"Choose a spell  •  Keys 1–9, 0, −, =",left+10,top+22,0xFF9EAFC7,false);
        if(filtered.size()>12)g.drawString(font,(page+1)+" / "+(Math.max(1,(filtered.size()+11)/12)),left+panelWidth-67,top+14,0xFFFFFFFF,false);
        hovered=null;super.render(g,mouseX,mouseY,partialTick);
        if(filtered.isEmpty())g.drawCenteredString(font,"No spells match your search.",width/2,top+82,0xFFB4C3D9);
        Spell s=hovered==null?wand.selected(stack):hovered;int level=wand.level(stack,s);
        g.drawString(font,s.title+" "+WandItem.roman(level)+"  •  "+SpellInfo.school(s.theme)+" / "+s.category().title,left+10,gridBottom+5,0xFF000000|s.theme.color,false);
        String stats=String.format(java.util.Locale.ROOT,"Base: %d mana · %.2fs cast · %.1fs cooldown%s",s.manaAtLevel(level),s.castTicks()/20.0,s.cooldown/20.0,s.damage>0?String.format(java.util.Locale.ROOT," · %.1f damage",s.damageAtLevel(level)):s.healing()>0?String.format(java.util.Locale.ROOT," · %.1f healing",s.healingAtLevel(level)):"");
        g.drawString(font,font.plainSubstrByWidth(stats,panelWidth-20),left+10,gridBottom+18,0xFFD5DFEF,false);
        int y=gridBottom+32;
        for(var line:font.split(Component.literal(s.description()),panelWidth-20)){
            if(y>height-27||y>gridBottom+66)break;g.drawString(font,line,left+10,y,0xFFB4C3D9,false);y+=10;
        }
        g.drawCenteredString(font,"Higher ranks: sneak-use with a matching scroll in your offhand",width/2,height-16,0xFF8F9EB8);
    }
    private final class Card extends AbstractButton {
        private final int slot;
        Card(int slot,int x,int y,int width,int height){super(x,y,width,height,Component.literal(wand.spells.get(slot).title));this.slot=slot;}
        @Override public void onPress(){choose(slot);}
        @Override protected void renderWidget(GuiGraphics g,int mouseX,int mouseY,float delta){
            Spell s=wand.spells.get(slot);boolean selected=wand.selected(stack)==s,focus=isHoveredOrFocused();int color=0xFF000000|s.theme.color;
            g.fill(getX(),getY(),getX()+width,getY()+height,focus?0xFF27334B:selected?0xFF1C293E:0xFF141F30);
            g.fill(getX(),getY()+height-2,getX()+width,getY()+height,selected||focus?color:0xFF33405A);
            var icon=ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"textures/gui/spell_icons/"+s.bookId()+".png");
            g.blit(icon,getX()+5,getY()+6,0,0,20,20,20,20);
            g.drawString(font,font.plainSubstrByWidth(s.title,width-34),getX()+29,getY()+6,0xFFE7EDF7,false);
            String rank=WandItem.roman(wand.level(stack,s))+" / "+WandItem.roman(s.levelCap())+" · "+s.manaAtLevel(wand.level(stack,s));
            g.drawString(font,font.plainSubstrByWidth(rank,width-34),getX()+29,getY()+18,0xFF9EAEC5,false);
            if(focus)hovered=s;
        }
        @Override protected void updateWidgetNarration(NarrationElementOutput output){defaultButtonNarrationText(output);}
    }
}
