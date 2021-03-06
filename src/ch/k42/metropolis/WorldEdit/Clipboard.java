package ch.k42.metropolis.WorldEdit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.k42.metropolis.generator.MetropolisGenerator;
import ch.k42.metropolis.minions.Cartesian;
import ch.k42.metropolis.minions.Constants;
import ch.k42.metropolis.minions.DecayOption;
import ch.k42.metropolis.model.enums.Direction;
import ch.k42.metropolis.model.enums.ContextType;


/**
 * Represents a structure defined by a loaded file in a cuboid.
 *
 * @author Daddy Churchill, Thomas Richner
 */

/*
 * TODO
 * - remove all those public variables, use getter&setter
 * - remove unused variables
 *
 */


public abstract class Clipboard {


    protected final static int nullspots_constant = 1; //HARDCODED

    protected SchematicConfig settings;
    protected GlobalSchematicConfig globalSettings;
    protected List<Cartesian> chests = new ArrayList<Cartesian>();
    protected List<Cartesian> spawners = new ArrayList<Cartesian>();

    protected String name;
    protected int groundLevelY = 1;
    protected List<ContextType> contextTypes;

    protected int sizeX;
    protected int sizeY;
    protected int sizeZ;
    protected int blockCount;

    protected int chunkX; /** Size in chunks */
    protected int chunkZ; /** Size in chunks */

    protected int insetNorth;
    protected int insetSouth;
    protected int insetWest;
    protected int insetEast;

	public Clipboard(MetropolisGenerator generator, File file, GlobalSchematicConfig globalSettings) throws Exception {
		this.name = file.getName();
        this.globalSettings = globalSettings;
		
		// grab the data
		load(generator, file);
		
		// finish figuring things out
		blockCount = sizeX * sizeY * sizeZ;
		
		chunkX = (sizeX + Constants.CHUNK_SIZE - 1) / Constants.CHUNK_SIZE;
		chunkZ = (sizeZ + Constants.CHUNK_SIZE - 1) / Constants.CHUNK_SIZE;
		
		int leftoverX = chunkX * Constants.CHUNK_SIZE - sizeX;
		int leftoverZ = chunkZ * Constants.CHUNK_SIZE - sizeZ;
		
		insetWest = leftoverX / 2;
		insetEast = leftoverX - insetWest;
		insetNorth = leftoverZ / 2;
		insetSouth = leftoverZ - insetNorth;
	}
	
	protected abstract void load(MetropolisGenerator generator, File file) throws Exception;

	public abstract void paste(MetropolisGenerator generator, int blockX,int blockZ, int streetLevel);

    //public abstract void setTrickOrTreat(MetropolisGenerator generator);

    @Override
    public String toString() {
        return "Clipboard{" +
                "sizeX=" + sizeX +
                ", sizeZ=" + sizeZ +
                ", name='" + name + '\'' +
                ", chunkX=" + chunkX +
                ", chunkZ=" + chunkZ +
                '}';
    }

    public List<ContextType> getContextTypes() {
        return contextTypes;
    }

    public Direction getDirection() {
        return settings.getDirection();
    }

    /**
     * Returns the block height between 0 and the first block
     * @param streetLevel the desired street level
     * @return height in blocks
     */
    public int getBottom(int streetLevel){
        return streetLevel-groundLevelY;
    }

    public DecayOption getDecayOptions() {
        return settings.getDecayOption();
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSizeZ() {
        return sizeZ;
    }

    public SchematicConfig getSettings() {
        return settings;
    }

    public List<Cartesian> getChests() {
        return chests;
    }

    public List<Cartesian> getSpawners() {
        return spawners;
    }

    public String getName() {
        return name;
    }
}
