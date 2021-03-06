package mc.euro.version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Version object: Capable of asking the important questions:. <br/><br/>
 * 
 * Is the version that's currently installed on the server compatible/supported with a specified version ? <br/><br/>
 * 
 * isCompatible(): Is the installed version greater than or equal to the minimum required version ? <br/><br/>
 * 
 * isSupported(): Is the installed version less than or equal to the maximum required version ? <br/><br/>
 * 
 * @author Europia79, BigTeddy98, Tux2, DSH105, Alkarinv
 */
public class Version<T> implements Comparable<Version> {
    
    /**
     * The Predicate tester preforms the job of checking if the plugin isEnabled(). <br/><br/>
     * 
     * Because, if the plugin is disabled, then our compatibility check should fail.
     */
    private final Tester<T> tester;
    private final String version;
    private String separator = "[_.-]";
    
    /**
     * VersionFactory methods getPluginVersion(), getServerVersion(), getNmsPackage() available for convenience. <br/>
     * @param version The version of the plugin, server, or application that is currently running in the JVM. <br/>
     */
    public Version(String version) {
        this.version = version;
        this.tester = TesterFactory.getDefaultTester();
    }
    
    /**
     * VersionFactory methods getPluginVersion(), getServerVersion(), getNmsPackage() available for convenience. <br/>
     * @param version The version of the plugin, server, or application that is currently running in the JVM. <br/>
     * @param tester isCompatible() & isSupported() will ask the tester if the "plugin" isEnabled() before proceeding. <br/>
     */
    public Version(String version, Tester<T> tester) {
        this.version = version;
        this.tester = tester;
    }
    
    /**
     * Checks if the plugin is enabled.
     * This method can be used to run any custom test
     * that you previously passed to the constructor,
     * then calls tester.test().
     * @return True is the plugin is enabled, or false if it is disabled.
     */
    public boolean isEnabled() {
        return tester.test();
    }
    
    /**
     * Alias for isGreaterThanOrEqualTo().
     * @param minVersion - The absolute minimum version that's required to achieve compatibility.
     * @return Return true, if the currently running/installed version is greater than or equal to minVersion.
     */
    public boolean isCompatible(Version minVersion) {
        return isCompatible(minVersion.toString());
    }
    
    /**
     * Alias for isGreaterThanOrEqualTo().
     * @param minVersion - The absolute minimum version that's required to achieve compatibility.
     * @return Return true, if the currently running/installed version is greater than or equal to minVersion.
     */
    public boolean isCompatible(String minVersion) {
        return isGreaterThanOrEqualTo(minVersion);
    }
    
    /**
     * Alias for isLessThanOrEqualTo().
     * @param maxVersion - The absolute maximum version that's supported.
     * @return Return true, if the currently running/installed version is less than or equal to maxVersion.
     */
    public boolean isSupported(Version maxVersion) {
        return isSupported(maxVersion.toString());
    }
    
    /**
     * Alias for isLessThanOrEqualTo().
     * @param maxVersion - The absolute maximum version that's supported.
     * @return Return true, if the currently running/installed version is less than or equal to maxVersion.
     */
    public boolean isSupported(String maxVersion) {
        return isLessThanOrEqualTo(maxVersion);
    }
    
    /**
     * Unlike isCompatible(), this method returns false if the versions are equal.
     * @param whichVersion
     * @return Return true, if the currently running/installed version is greater than whichVersion.
     */
    public boolean isGreaterThan(Version whichVersion) {
        return isGreaterThan(whichVersion.toString());
    }
    
    /**
     * Unlike isCompatible(), this method returns false if the versions are equal.
     * @param whichVersion
     * @return Return true, if the currently running/installed version is greater than whichVersion.
     */
    public boolean isGreaterThan(String whichVersion) {
        if (!this.isEnabled()) return false;
        int x = compareTo(whichVersion);
        if (x > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Alias for isCompatible().
     * @param minVersion
     * @return Return true, if this version object is greater than or equal to the parameter, minVersion.
     */
    public boolean isGreaterThanOrEqualTo(Version minVersion) {
        return isGreaterThanOrEqualTo(minVersion.toString());
    }
    
    /**
     * Alias for isCompatible().
     * @param minVersion
     * @return Return true, if this version object is greater than or equal to the parameter, minVersion.
     */
    public boolean isGreaterThanOrEqualTo(String minVersion) {
        if (!this.isEnabled()) return false;
        int x = compareTo(new Version(minVersion));
        if (x >= 0) {
            return true;
        } 
        return false;
    }
    
    /**
     * Unlike isSupported(), this method returns false if the versions are equal.
     * @param whichVersion
     * @return Return true, if the currently running/installed version is less than whichVersion.
     */
    public boolean isLessThan(Version whichVersion) {
        return isLessThan(whichVersion.toString());
    }
    
    /**
     * Unlike isSupported(), this method returns false if the versions are equal.
     * @param whichVersion
     * @return Return true, if the currently running/installed version is less than whichVersion.
     */
    public boolean isLessThan(String whichVersion) {
        if (!this.isEnabled()) return false;
        int x = compareTo(whichVersion);
        if (x < 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Alias for isSupported().
     * @param maxVersion
     * @return Return true, if this version object is less than or equal to the parameter, maxVersion.
     */
    public boolean isLessThanOrEqualTo(Version maxVersion) {
        return isLessThanOrEqualTo(maxVersion.toString());
    }
    
    /**
     * Alias for isSupported().
     * @param maxVersion
     * @return Return true, if this version object is less than or equal to the parameter, maxVersion.
     */
    public boolean isLessThanOrEqualTo(String maxVersion) {
        if (!this.isEnabled()) return false;
        int x = compareTo(new Version(maxVersion));
        if (x <= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns Negative, Zero, or Positive if this version is less than, equal to, or greater than the parameter.
     * Unlike isCompatible() & isSupported(): THIS METHOD DOES NOT CHECK IF THE PLUGIN IS ENABLED.
     * @param whichVersion
     * @return Negative, Zero, or Positive as this object is less than, equal to, or greater than the parameter.
     */
    @Override
    public int compareTo(Version whichVersion) {
        return compareTo(whichVersion.toString());
    }
    
    /**
     * Returns Negative, Zero, or Positive if this version is less than, equal to, or greater than the parameter.
     * Unlike isCompatible() and isSupported(),
     * THIS METHOD DOES NOT CHECK IF THE PLUGIN IS ENABLED.
     * @param whichVersion
     * @return Negative, Zero, or Positive as this object is less than, equal to, or greater than the parameter.
     */
    public int compareTo(String whichVersion) {
        int[] currentVersion = parseVersion(this.version);
        int[] otherVersion = parseVersion(whichVersion);
        int length = Math.max(currentVersion.length, otherVersion.length);
        for (int index = 0; index < length; index = index + 1) {
            int self = (index < currentVersion.length) ? currentVersion[index] : 0;
            int other = (index < otherVersion.length) ? otherVersion[index] : 0;
            
            if (self != other) {
                return self - other;
            }
        }
        return 0;
    }
    
    /**
     * A typical version of 1.2.3.4-b567 will be broken down into an array. <br/><br/>
     * 
     * [1] [2] [3] [4] [567]
     */
    private int[] parseVersion(String versionParam) {
        versionParam = (versionParam == null) ? "" : versionParam;
        String[] stringArray = versionParam.split(separator);
        int[] temp = new int[stringArray.length];
        for (int index = 0; index <= (stringArray.length - 1); index = index + 1) {
            String t = stringArray[index].replaceAll("\\D", "");
            try {
                temp[index] = Integer.valueOf(t);
            } catch(NumberFormatException ex) {
                temp[index] = 0;
            }
        }
        return temp;
    }
    
    /**
     * The default regex separator is "[_.-]".
     * Which separates by underscores, periods, & dashes.
     */
    public Version setSeparator(String regex) {
        this.separator = regex;
        return this;
    }
    
    /**
     * search() for possible Development builds.
     */
    public boolean search(String regex) {
        if (version == null) return false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.version);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * Used to get a Sub-Version (or Development build). <br/><br/>
     * @param regex 
     * @return A completely new Version object.
     */
    public Version getSubVersion(String regex) {
        if (version == null) return this;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.version);
        String dev = this.version;
        if (matcher.find()) {
            dev = matcher.group();
        }
        return new Version(dev);
    }
    
    /**
     * This is the exact, un-altered String that was passed to the constructor.
     * @return The String representation, or an empty String if null.
     */
    @Override
    public String toString() {
        return (this.version == null) ? "" : this.version;
    }
}
