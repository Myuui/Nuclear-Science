package nuclearscience.common.tile;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileControlRodAssembly extends GenericTile {

	public static final Direction[] HORIZONTAL_DIRECTIONS = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

	public Property<Integer> direction = property(new Property<>(PropertyType.Integer, "direction", Direction.DOWN.ordinal()));
	public final Property<Integer> insertion = property(new Property<>(PropertyType.Integer, "insertion", 0));
	public final Property<Boolean> isMSR = property(new Property<>(PropertyType.Boolean, "isMSR", false));

	public TileControlRodAssembly(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_CONTROLRODASSEMBLY.get(), pos, state);
		addComponent(new ComponentTickable());
		addComponent(new ComponentPacketHandler());
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor) {
		super.onNeightborChanged(neighbor);
		isMSR.set(false);
		for (Direction dir : HORIZONTAL_DIRECTIONS) {
			BlockEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
			if (tile instanceof TileMSRReactorCore) {
				isMSR.set(true);
				direction.set(dir.ordinal());
				break;
			}

		}

	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		isMSR.set(false);
		for (Direction dir : HORIZONTAL_DIRECTIONS) {
			BlockEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
			if (tile instanceof TileMSRReactorCore) {
				isMSR.set(true);
				direction.set(dir.ordinal());
				break;
			}

		}
	}

}
