package org.bukkit.craftbukkit.v1_20_R3.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R3.projectiles.CraftBlockProjectileSource;
import org.bukkit.inventory.Inventory;
import org.bukkit.projectiles.BlockProjectileSource;

public class CraftDispenser extends CraftLootable<DispenserBlockEntity> implements Dispenser {

    public CraftDispenser(World world, final DispenserBlockEntity te) {
        super(world, te);
    }

    protected CraftDispenser(CraftDispenser state) {
        super(state);
    }

    @Override
    public Inventory getSnapshotInventory() {
        return new CraftInventory(this.getSnapshot());
    }

    @Override
    public Inventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventory(this.getTileEntity());
    }

    @Override
    public BlockProjectileSource getBlockProjectileSource() {
        Block block = getBlock();

        if (block.getType() != Material.DISPENSER) {
            return null;
        }

        return new CraftBlockProjectileSource((DispenserBlockEntity) this.getTileEntityFromWorld());
    }

    @Override
    public boolean dispense() {
        ensureNoWorldGeneration();
        Block block = getBlock();
        if (block.getType() == Material.DISPENSER) {
            CraftWorld world = (CraftWorld) this.getWorld();
            DispenserBlock dispense = (DispenserBlock) Blocks.DISPENSER;

            dispense.dispenseFrom(world.getHandle(), this.getHandle(), this.getPosition());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CraftDispenser copy() {
        return new CraftDispenser(this);
    }
}
