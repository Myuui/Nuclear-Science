package nuclearscience.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.screen.GenericCustomScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.References;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.tile.TileReactorCore;

@OnlyIn(Dist.CLIENT)
public class ScreenReactorCore extends GenericCustomScreen<ContainerReactorCore> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/reactorcore.png");

	public ScreenReactorCore(ContainerReactorCore container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
	}

	@Override
	public ResourceLocation getScreenBackground() {
		return SCREEN_BACKGROUND;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		font.draw(matrixStack, Component.translatable("gui.reactorcore.deuterium"), titleLabelX, (float) titleLabelY + 14, 4210752);
		TileReactorCore core = menu.getHostFromIntArray();
		if (core != null) {
			font.draw(matrixStack, Component.translatable("gui.reactorcore.temperature", core.temperature.get().intValue() / 4 + 15 + " C"), titleLabelX, (float) titleLabelY + 14 * 3, 4210752);
			if (core.temperature.get() > TileReactorCore.MELTDOWN_TEMPERATURE_ACTUAL && System.currentTimeMillis() % 1000 < 500) {
				font.draw(matrixStack, Component.translatable("gui.reactorcore.warning"), titleLabelX, (float) titleLabelY + 55, 16711680);
			}
		}
	}
}