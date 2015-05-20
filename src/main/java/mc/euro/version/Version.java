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
 * @author Europia79, BigTeddy98, Tux2, DSH105
 */
public class Version<T> implements Comparable<Version> {
    
    /**
     * The Predicate tester preforms the job of checking if the plugin isEnabled(). <br/><br/>
     * 
     * Because, if the plugin is disabled, then our compatibility check should fail.
     */
    Tester<T> tester;
    Object object;
    final String version;
    String separator = "[_.-]";
    
    /**
     * VersionFactory methods getPluginVersion(), getServerVersion(), getNmsVersion() available for convenience. <br/>
     * @param version The version of the plugin, server, or application that is currently running in the JVM. <br/>
     */
    public Version(String version) {
        this.version = version;
        this.tester = TesterFactory.getDefaultTester();
        this.object = new Object();
    }
    
    /**
     * VersionFactory methods getPluginVersion(), getServerVersion(), getNmsVersion() available for convenience. <br/>
     * @param version The version of the plugin, server, or application that is currently running in the JVM. <br/>
     * @param tester isCompatible() & isSupported() will ask the tester if the object isEnabled() before proceeding. <br/>
     * @param object The object to be tested. <br/>
     */
    public Version(String version, Tester tester, Object object) {
        this.version = version;
        this.tester = tester;
        this.object = object;
    }
    
    public boolean isEnabled() {
        return tester.isEnabled((T) object);
    }
    
    /**
     * @param minVersion - The absolute minimum version that's required to achieve compatibility.
     * @return Return true, if the currently running/installed version is greater than or equal to minVersion.
     */
    public boolean isCompatible(String minVersion) {
        if (!this.isEnabled()) return false;
        int x = compareTo(new Version(minVersion));
        if (x >= 0) {
            return true;
        } 
        return false;
    }
    
    /**
     * @param maxVersion - The absolute maximum version that's supported.
     * @return Return true, if the currently running/installed version is less than or equal to maxVersion.
     */
    public boolean isSupported(String maxVersion) {
        if (!this.isEnabled()) return false;
        int x = compareTo(new Version(maxVersion));
        if (x <= 0) {
            return true;
        }
        return false;
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
    public boolean isGreaterThanOrEqualTo(String minVersion) {
        return isCompatible(minVersion);
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
    public boolean isLessThanOrEqualTo(String maxVersion) {
        return isSupported(maxVersion);
    }
    
    /**
     * Returns Negative, Zero, or Positive if this version is less than, equal to, or greater than the parameter.
     * @param whichVersion
     * @return Negative, Zero, or Positive as this object is less than, equal to, or greater than the parameter.
     */
    public int compareTo(String whichVersion) {
        return compareTo(new Version(whichVersion));
    }
    
    @Override
    public int compareTo(Version whichVersion) {
        int[] currentVersion = parseVersion(this.version);
        int[] otherVersion = parseVersion(whichVersion.toString());
        int length = (currentVersion.length >= otherVersion.length) ? currentVersion.length : otherVersion.length;
        for (int index = 0; index < length; index = index + 1) {
            try {
                if (currentVersion[index] != otherVersion[index]) {
                    return currentVersion[index] - otherVersion[index];
                }
            } catch (IndexOutOfBoundsException ex) {
                if (currentVersion.length > otherVersion.length) {
                    return currentVersion[index] - 0;
                } else if (currentVersion.length < otherVersion.length) {
                    return 0 - otherVersion[index];
                }
            }
        }
        return 0;
    }
    
    /**
     * A typical version of 1.2.3.4-b567 will be broken down into an array. <br/><br/>
     * 
     * [1] [2] [3] [4] [567]
     */
    private int[] parseVersion(String version) {
        String[] stringArray = version.split(separator);
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
    
    @Override
    public String toString() {
        String v = (this.version == null) ? "" : this.version;
        return v;
    }
}
