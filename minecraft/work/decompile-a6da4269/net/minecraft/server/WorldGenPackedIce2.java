package net.minecraft.server;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;

public class WorldGenPackedIce2 extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {

    public WorldGenPackedIce2(Function<Dynamic<?>, ? extends WorldGenFeatureEmptyConfiguration> function) {
        super(function);
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration worldgenfeatureemptyconfiguration) {
        while (generatoraccess.isEmpty(blockposition) && blockposition.getY() > 2) {
            blockposition = blockposition.down();
        }

        if (generatoraccess.getType(blockposition).getBlock() != Blocks.SNOW_BLOCK) {
            return false;
        } else {
            blockposition = blockposition.up(random.nextInt(4));
            int i = random.nextInt(4) + 7;
            int j = i / 4 + random.nextInt(2);

            if (j > 1 && random.nextInt(60) == 0) {
                blockposition = blockposition.up(10 + random.nextInt(30));
            }

            int k;
            int l;

            for (k = 0; k < i; ++k) {
                float f = (1.0F - (float) k / (float) i) * (float) j;

                l = MathHelper.f(f);

                for (int i1 = -l; i1 <= l; ++i1) {
                    float f1 = (float) MathHelper.a(i1) - 0.25F;

                    for (int j1 = -l; j1 <= l; ++j1) {
                        float f2 = (float) MathHelper.a(j1) - 0.25F;

                        if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f) && (i1 != -l && i1 != l && j1 != -l && j1 != l || random.nextFloat() <= 0.75F)) {
                            IBlockData iblockdata = generatoraccess.getType(blockposition.b(i1, k, j1));
                            Block block = iblockdata.getBlock();

                            if (iblockdata.isAir() || b(block) || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
                                this.a(generatoraccess, blockposition.b(i1, k, j1), Blocks.PACKED_ICE.getBlockData());
                            }

                            if (k != 0 && l > 1) {
                                iblockdata = generatoraccess.getType(blockposition.b(i1, -k, j1));
                                block = iblockdata.getBlock();
                                if (iblockdata.isAir() || b(block) || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
                                    this.a(generatoraccess, blockposition.b(i1, -k, j1), Blocks.PACKED_ICE.getBlockData());
                                }
                            }
                        }
                    }
                }
            }

            k = j - 1;
            if (k < 0) {
                k = 0;
            } else if (k > 1) {
                k = 1;
            }

            for (int k1 = -k; k1 <= k; ++k1) {
                l = -k;

                while (l <= k) {
                    BlockPosition blockposition1 = blockposition.b(k1, -1, l);
                    int l1 = 50;

                    if (Math.abs(k1) == 1 && Math.abs(l) == 1) {
                        l1 = random.nextInt(5);
                    }

                    while (true) {
                        if (blockposition1.getY() > 50) {
                            IBlockData iblockdata1 = generatoraccess.getType(blockposition1);
                            Block block1 = iblockdata1.getBlock();

                            if (iblockdata1.isAir() || b(block1) || block1 == Blocks.SNOW_BLOCK || block1 == Blocks.ICE || block1 == Blocks.PACKED_ICE) {
                                this.a(generatoraccess, blockposition1, Blocks.PACKED_ICE.getBlockData());
                                blockposition1 = blockposition1.down();
                                --l1;
                                if (l1 <= 0) {
                                    blockposition1 = blockposition1.down(random.nextInt(5) + 1);
                                    l1 = random.nextInt(5);
                                }
                                continue;
                            }
                        }

                        ++l;
                        break;
                    }
                }
            }

            return true;
        }
    }
}
