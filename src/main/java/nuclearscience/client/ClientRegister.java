package nuclearscience.client;

import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.client.guidebook.ModuleNuclearScience;
import nuclearscience.client.render.entity.RenderParticle;
import nuclearscience.client.render.tile.RenderAtomicAssembler;
import nuclearscience.client.render.tile.RenderChemicalExtractor;
import nuclearscience.client.render.tile.RenderFusionReactorCore;
import nuclearscience.client.render.tile.RenderGasCentrifuge;
import nuclearscience.client.render.tile.RenderNuclearBoiler;
import nuclearscience.client.render.tile.RenderQuantumCapacitor;
import nuclearscience.client.render.tile.RenderReactorCore;
import nuclearscience.client.render.tile.RenderRodAssembly;
import nuclearscience.client.render.tile.RenderTeleporter;
import nuclearscience.client.render.tile.RenderTurbine;
import nuclearscience.client.screen.ScreenAtomicAssembler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenFreezePlug;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenMSRFuelPreProcessor;
import nuclearscience.client.screen.ScreenMSRReactorCore;
import nuclearscience.client.screen.ScreenMoltenSaltSupplier;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenQuantumCapacitor;
import nuclearscience.client.screen.ScreenRadioactiveProcessor;
import nuclearscience.client.screen.ScreenRadioisotopeGenerator;
import nuclearscience.client.screen.ScreenReactorCore;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceEntities;
import nuclearscience.registers.NuclearScienceMenuTypes;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {

	@SubscribeEvent
	public static void onModelEvent(RegisterAdditional event) {
		event.register(MODEL_GASCENTRIFUGECENTER);
		event.register(MODEL_TURBINECASING);
		event.register(MODEL_TURBINEROTORLAYER);
		event.register(MODEL_REACTORCORE);
		event.register(MODEL_REACTORFUELROD);
		event.register(MODEL_REACTORDEUTERIUM);
		event.register(MODEL_CONTROLRODASSEMBLYSTRUCTURE);
		event.register(MODEL_CONTROLRODASSEMBLYSROD);
	}

	public static final ResourceLocation MODEL_GASCENTRIFUGECENTER = new ResourceLocation(References.ID + ":block/gascentrifugecenter");
	public static final ResourceLocation MODEL_TURBINECASING = new ResourceLocation(References.ID + ":block/turbinecasing");
	public static final ResourceLocation MODEL_TURBINEROTORLAYER = new ResourceLocation(References.ID + ":block/turbinerotorlayer");
	public static final ResourceLocation MODEL_REACTORCORE = new ResourceLocation(References.ID + ":block/reactorcore");
	public static final ResourceLocation MODEL_REACTORFUELROD = new ResourceLocation(References.ID + ":block/reactorfuelrod");
	public static final ResourceLocation MODEL_REACTORDEUTERIUM = new ResourceLocation(References.ID + ":block/reactordeuterium");
	public static final ResourceLocation MODEL_CONTROLRODASSEMBLYSTRUCTURE = new ResourceLocation(References.ID + ":block/controlrodassemblystructure");
	public static final ResourceLocation MODEL_CONTROLRODASSEMBLYSROD = new ResourceLocation(References.ID + ":block/controlrodassemblyrod");
	public static final ResourceLocation TEXTURE_REACTORCOREEMPTY = new ResourceLocation(References.ID + ":textures/model/reactorcore.png");

	public static void setup() {
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_NUCLEARBOILER.get(), ScreenNuclearBoiler::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), ScreenFreezePlug::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), ScreenReactorCore::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), ScreenParticleInjector::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_QUANTUMCAPACITOR.get(), ScreenQuantumCapacitor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), ScreenMSRReactorCore::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), ScreenAtomicAssembler::new);

		ScreenGuidebook.addGuidebookModule(new ModuleNuclearScience());
	}

	@SubscribeEvent
	public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_TURBINE.get(), RenderTurbine::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_REACTORCORE.get(), RenderReactorCore::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_QUANTUMCAPACITOR.get(), RenderQuantumCapacitor::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_TELEPORTER.get(), RenderTeleporter::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_CONTROLRODASSEMBLY.get(), RenderRodAssembly::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_ATOMICASSEMBLER.get(), RenderAtomicAssembler::new);

		event.registerEntityRenderer(NuclearScienceEntities.ENTITY_PARTICLE.get(), RenderParticle::new);

	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

}
