package ivchain;

import com.pixelmonmod.pixelmon.Pixelmon;
import ivchain.capability.CapabilityChainTracker;
import ivchain.capability.ChainingPlayer;
import ivchain.capability.IChainTracker;
import ivchain.command.ChainCommand;
import ivchain.event.IVChainEventHandler;
import ivchain.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.Random;

@Mod(modid = "ivchain", name = "IV Chaining", version = "1.2.0", dependencies = "required-after:pixelmon@[7.0.7,)", acceptableRemoteVersions = "*")
public class IVChain {
    @Mod.Instance(value = "ivchain")
    public static IVChain instance;

    @SidedProxy(clientSide = "ivchain.proxy.ClientProxy", serverSide = "ivchain.proxy.CommonProxy")
    public static CommonProxy proxy;

    public Random rand = new Random();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        IVConfig.init(evt.getSuggestedConfigurationFile());
        CapabilityManager.INSTANCE.register(IChainTracker.class, new CapabilityChainTracker.ChainTrackingImpl<>(), ChainingPlayer.class);
        MinecraftForge.EVENT_BUS.register(new IVChainEventHandler.ForgeHandler());
        Pixelmon.EVENT_BUS.register(new IVChainEventHandler.PixelHandler());
        IVRegistry.init();
    }

    @Mod.EventHandler
    public void init(FMLServerStartingEvent evt) {
        evt.registerServerCommand(new ChainCommand());
    }
}
