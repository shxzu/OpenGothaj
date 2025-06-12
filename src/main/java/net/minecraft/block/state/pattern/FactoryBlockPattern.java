package net.minecraft.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class FactoryBlockPattern {
    private static final Joiner COMMA_JOIN = Joiner.on(",");
    private final List<String[]> depth = Lists.newArrayList();
    private final Map<Character, Predicate<BlockWorldState>> symbolMap = Maps.newHashMap();
    private int aisleHeight;
    private int rowWidth;

    private FactoryBlockPattern() {
        this.symbolMap.put(Character.valueOf(' '), Predicates.alwaysTrue());
    }

    public FactoryBlockPattern aisle(String ... aisle) {
        if (!ArrayUtils.isEmpty(aisle) && !StringUtils.isEmpty(aisle[0])) {
            if (this.depth.isEmpty()) {
                this.aisleHeight = aisle.length;
                this.rowWidth = aisle[0].length();
            }
            if (aisle.length != this.aisleHeight) {
                throw new IllegalArgumentException("Expected aisle with height of " + this.aisleHeight + ", but was given one with a height of " + aisle.length + ")");
            }
            String[] stringArray = aisle;
            int n = aisle.length;
            int n2 = 0;
            while (n2 < n) {
                String s = stringArray[n2];
                if (s.length() != this.rowWidth) {
                    throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.rowWidth + ", found one with " + s.length() + ")");
                }
                char[] cArray = s.toCharArray();
                int n3 = cArray.length;
                int n4 = 0;
                while (n4 < n3) {
                    char c0 = cArray[n4];
                    if (!this.symbolMap.containsKey(Character.valueOf(c0))) {
                        this.symbolMap.put(Character.valueOf(c0), null);
                    }
                    ++n4;
                }
                ++n2;
            }
            this.depth.add(aisle);
            return this;
        }
        throw new IllegalArgumentException("Empty pattern for aisle");
    }

    public static FactoryBlockPattern start() {
        return new FactoryBlockPattern();
    }

    public FactoryBlockPattern where(char symbol, Predicate<BlockWorldState> blockMatcher) {
        this.symbolMap.put(Character.valueOf(symbol), blockMatcher);
        return this;
    }

    public BlockPattern build() {
        return new BlockPattern(this.makePredicateArray());
    }

    private Predicate<BlockWorldState>[][][] makePredicateArray() {
        this.checkMissingPredicates();
        Predicate[][][] predicate = (Predicate[][][])Array.newInstance(Predicate.class, this.depth.size(), this.aisleHeight, this.rowWidth);
        int i = 0;
        while (i < this.depth.size()) {
            int j = 0;
            while (j < this.aisleHeight) {
                int k = 0;
                while (k < this.rowWidth) {
                    predicate[i][j][k] = this.symbolMap.get(Character.valueOf(this.depth.get(i)[j].charAt(k)));
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return predicate;
    }

    private void checkMissingPredicates() {
        ArrayList<Character> list = Lists.newArrayList();
        for (Map.Entry<Character, Predicate<BlockWorldState>> entry : this.symbolMap.entrySet()) {
            if (entry.getValue() != null) continue;
            list.add(entry.getKey());
        }
        if (!list.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + COMMA_JOIN.join(list) + " are missing");
        }
    }
}
