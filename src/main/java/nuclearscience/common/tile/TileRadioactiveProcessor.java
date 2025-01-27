package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileRadioactiveProcessor extends GenericTile {

	public static final int MAX_TANK_CAPACITY = 5000;

	public TileRadioactiveProcessor(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_RADIOACTIVEPROCESSOR.get(), pos, state);
		addComponent(new ComponentTickable());
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).relativeInput(Direction.NORTH));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 0, MAX_TANK_CAPACITY).setInputDirections(Direction.UP).setRecipeType(NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE.get()));
		addComponent(new ComponentInventory(this).size(6).inputs(1).outputs(1).bucketInputs(1).upgrades(3).processors(1).processorInputs(1).validUpgrades(ContainerRadioactiveProcessor.VALID_UPGRADES).valid(machineValidator()).faceSlots(Direction.UP, 0).faceSlots(Direction.DOWN, 1).slotFaces(2, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST));
		addComponent(new ComponentProcessor(this).canProcess(this::shouldProcessRecipe).process(component -> component.processFluidItem2ItemRecipe(component)));
		addComponent(new ComponentContainerProvider("container.radioactiveprocessor").createMenu((id, player) -> new ContainerRadioactiveProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private boolean shouldProcessRecipe(ComponentProcessor component) {
		component.consumeBucket();
		boolean canProcess = component.canProcessFluidItem2ItemRecipe(component, NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE.get());
		if (BlockEntityUtils.isLit(this) ^ canProcess) {
			BlockEntityUtils.updateLit(this, canProcess);
		}
		return canProcess;
	}

}
