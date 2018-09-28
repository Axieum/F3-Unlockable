package me.axieum.mcmod.f3unlockable.api.util;

public enum F3Aspect
{
    X("x"),
    Y("y"),
    Z("z"),
    CHUNK_X("chunk_x"),
    CHUNK_Y("chunk_y"),
    CHUNK_Z("chunk_z"),
    FACING("facing"),
    DIFFICULTY("difficulty"),
    DAY("day");

    private final String name;

    /**
     * Constructor.
     *
     * @param name The name of the aspect
     */
    F3Aspect(String name)
    {
        this.name = name;
    }

    /**
     * Get a new F3Aspect by name.
     *
     * @param name The target aspect name
     * @return The aspect with the specified name or null if none found
     */
    public static F3Aspect getByName(String name)
    {
        for (F3Aspect aspect : values())
            if (aspect.toString().equalsIgnoreCase(name))
                return aspect;

        return null;
    }

    /**
     * If a name is a valid aspect.
     *
     * @param name The name of the aspect
     * @return True if the name is present
     */
    public static boolean has(String name)
    {
        return getByName(name) != null;
    }

    /**
     * Retrieve all valid aspect names.
     *
     * @return An array of valid aspect names
     */
    public static String[] allNames()
    {
        String[] arr = new String[values().length];
        for (int i = 0; i < arr.length; i++)
            arr[i] = values()[i].toString();

        return arr;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
