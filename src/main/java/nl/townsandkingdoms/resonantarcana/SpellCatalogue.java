package nl.townsandkingdoms.resonantarcana;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/** Shared defaults consumed by gameplay, tooltips, art generation and documentation. */
final class SpellCatalogue {
    static final int POWER_PER_LEVEL;
    private static final Map<String,Definition> SPELLS;
    private static final Map<String,SchoolDefinition> SCHOOLS;
    static {
        try(var input=SpellCatalogue.class.getResourceAsStream("/assets/resonant_arcana/catalogue.json")){
            if(input==null)throw new IllegalStateException("Missing spell catalogue");
            var root=JsonParser.parseReader(new InputStreamReader(input,StandardCharsets.UTF_8)).getAsJsonObject();
            if(root.get("schemaVersion").getAsInt()!=2)throw new IllegalStateException("Unsupported spell catalogue version");
            POWER_PER_LEVEL=root.get("powerPerLevel").getAsInt();
            if(POWER_PER_LEVEL<1||POWER_PER_LEVEL>100)throw new IllegalStateException("Invalid level scaling");
            var gson=new Gson();var spells=new LinkedHashMap<String,Definition>();
            for(var entry:root.getAsJsonObject("spells").entrySet()){
                Definition d=gson.fromJson(entry.getValue(),Definition.class);d.validate(entry.getKey());spells.put(entry.getKey(),d);
            }
            var schools=new LinkedHashMap<String,SchoolDefinition>();
            for(var entry:root.getAsJsonArray("schools")){
                SchoolDefinition d=gson.fromJson(entry,SchoolDefinition.class);
                if(d.id()==null||d.title()==null||d.spells()==null||(d.spells().isEmpty()||d.spells().size()>128)||schools.put(d.id(),d)!=null)
                    throw new IllegalStateException("Invalid school definition");
            }
            SPELLS=Collections.unmodifiableMap(spells);SCHOOLS=Collections.unmodifiableMap(schools);
        }catch(Exception e){throw new ExceptionInInitializerError(e);}
    }
    private SpellCatalogue(){}
    static Definition definition(String id){
        Definition d=SPELLS.get(id);if(d==null)throw new IllegalStateException("Missing spell definition: "+id);return d;
    }
    static SchoolDefinition school(String id){
        var d=SCHOOLS.get(id);if(d==null)throw new IllegalArgumentException("Unknown school: "+id);return d;
    }
    static void validateRegistry(){
        var ids=new HashSet<String>();
        for(var school:SCHOOLS.values())for(String id:school.spells()){
            var spell=Spell.valueOf(id);
            if(!ids.add(id))throw new IllegalStateException("Invalid school home for "+id);
        }
        if(ids.size()!=Spell.values().length||SPELLS.size()!=ids.size())throw new IllegalStateException("Catalogue and spell registry differ");
    }
    record SchoolDefinition(String id,String title,List<String> spells){
        Spell[] catalogue(){return spells.stream().map(Spell::valueOf).filter(Spell::available).toArray(Spell[]::new);}
    }
    record Definition(String title,String kind,String theme,float damage,double radius,double range,int cooldown,
                      int castTicks,int mana,int manaStep,int maxLevel,float healing,int duration,int interval,
                      int pulses,String role,String description,String bookId,String category){
        void validate(String id){
            if(title==null||title.isBlank()||kind==null||theme==null||role==null||description==null||description.isBlank()
                ||category==null||!java.util.Arrays.stream(SpellCategory.values()).anyMatch(c->c.name().equals(category))||bookId==null||!bookId.matches("[a-z0-9_]+")||!Float.isFinite(damage)||damage<0||damage>256
                ||!Double.isFinite(radius)||radius<0||radius>16||!Double.isFinite(range)||range<0||range>64
                ||cooldown<1||castTicks<0||castTicks>400||mana<1||mana>500||manaStep<0||maxLevel<1||maxLevel>5
                ||!Float.isFinite(healing)||healing<0||duration<0||duration>600||interval<0||pulses<1||pulses>16)
                throw new IllegalStateException("Invalid spell definition: "+id);
        }
    }
}
