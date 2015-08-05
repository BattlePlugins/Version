Version Lib
======
Allows version checking of whatever is running on the server 
before running version-dependent code.


The API:
---
- public boolean **isCompatible(**String minVersion**)**
  * @param minVersion - The absolute minimum version that's required to achieve compatibility.
  * @return Return true, if the currently running/installed version is greater than or equal to minVersion.
- public boolean **isSupported(**String maxVersion**)**
  * @param maxVersion - The absolute maximum version that's supported.
  * @return Return true, if the currently running/installed version is less than or equal to maxVersion.
- constructor Version(String version)
  * @param version - This is the version that is running on the server.
- constructor Version(String version, Tester tester)
  * @param tester - This is an optional interface that you can pass in.
  * tester.test() will execute before isCompatible() & isSupported()
    * tester.test() must return true or else isCompatible() & isSupported() will return false.
    * true = continue the version checking.
    * false = stop the version checking and return false.
  * tester.test() can be any custom test that you want to perform.
    * For example, you can wrap plugin.isEnabled()
    * Or, you can attach an test to see if the plugin is in a broken state: Most likely from an Exception during onEnable()
    * Or, anything else your heart desires :P
    
     

   
Examples using VersionFactory construction:
---
```java
public class SomePlugin extends JavaPlugin {
    
    private Version server;
    public static final String MAX = "1.7.10-R9.9-SNAPSHOT";
    public static final String MIN = "1.2.5";
    public static final String NMS = VersionFactory.getNmsPackage();

    @Override
    public void onEnable() {
        server = VersionFactory.getMinecraftVersion();
        if (!server.isSupported(MAX) || !server.isCompatible(MIN)) {
            getLogger().info("This plugin is not compatible with your server.");
            getLogger().info("The maximum supported version is " + MAX);
            getLogger().info("The minimum compatible version is " + MIN);
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        // ...
        Version HD = VersionFactory.getPluginVersion("HolographicDisplays");
        Version Holoapi = VersionFactory.getPluginVersion("HoloAPI");
        debug.log("HolographicDisplays version = " + HD.toString());
        debug.log("HoloAPI version = " + Holoapi.toString());
        if (ShowHolograms && HD.isCompatible("1.8.5")) {
            this.holograms = new HolographicDisplay(this);
            debug.log("HolographicDisplays support is enabled.");
        } else if (ShowHolograms && Holoapi.isEnabled()) {
            this.holograms = new HolographicAPI(this);
            debug.log("HoloAPI support is enabled.");
        } else {
            this.holograms = new HologramsOff();
            debug.log("Hologram support is disabled.");
            debug.log("Please download HoloAPI or HolographicDisplays to enable Hologram support.");
        }
    }
    
    public static Class<?> getNmsClass(String clazz) throws Exception {
        return Class.forName("com.yourdomain.yourproject.compat." + NMS + "." + clazz);
    }
}
```
  
  
Maven Repository:
---

~~[http://rainbowcraft.sytes.net/maven/repository/] (http://rainbowcraft.sytes.net/maven/repository/ "Maven Repository")~~

If you use maven, put these declarations in your pom.xml:

~~**repositories section:**~~

Unfortunately, I have removed this repository. So you will have to install the project to your local ```.m2/repository```

```xml
<repository>
    <id>rainbowcraft-repo</id>
    <url>http://rainbowcraft.sytes.net/maven/repository/</url>
</repository>
```

**Installation to your local .m2/repository**

***git latest version:***

* ```git clone https://github.com/Europia79/Version.git```
* ```git clean install```

***git previous versions:***
* ```git clone https://github.com/Europia79/Version.git```
* ```git log --format=oneline```
* ```git checkout <hash>```
* ```git clean install```
* ```git checkout master```

***ci file download & mvn install:***

* ~~[http://ci.battleplugins.com/job/Version/](http://ci.battleplugins.com/job/Version/ "Version downloads")~~
* Or, you can download a jar and run the ```mvn install:install-file``` command.
* This is also helpful to install any dependencies that maven can't automatically download.
  * Arguments: 
    * ```-Dfile=``` : The name & location of the jar
    * ```-DgroupId=``` : Mine is ```mc.euro```
    * ```-DartifactId=``` : If you decompile or unzip the jar, then you can find this & other information inside the folder ```META-INF/maven/{groupId}.{artifactId}/pom.properties``` & ```pom.xml```
    * ```-Dversion``` : Also found in ```pom.properties``` & ```pom.xml```
    * ```Dpackaging=jar```
    * ```DcreateChecksum=true```
  * Example: ```mvn install:install-file -Dfile="C:\Users\Nikolai\Documents\lib\version\2.0.1\Version.jar" -DgroupId=mc.euro -DartifactId=Version -Dversion=2.0.1 -Dpackaging=jar -DcreateChecksum=true```

**dependencies section:**

I recommend using ```2.0.1``` or ```3.0.0-SNAPSHOT```

```xml
<dependency>
    <groupId>mc.euro</groupId>
    <artifactId>Version</artifactId>
    <version>2.0.1</version>
    <scope>compile</scope>
</dependency>
```

**build-plugins section:**

```xml
<build>
    <plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>1.6</version>
        <executions>
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>shade</goal>
                </goals>
                <configuration>
                    <relocations>
                        <relocation>
                            <pattern>mc.euro.version</pattern>
                            <shadedPattern>${project.groupId}.${project.artifactId}.version</shadedPattern>
                        </relocation>
                    </relocations>
                    <artifactSet>
                        <includes>
                            <include>mc.euro:Version</include>
                        </includes>
                    </artifactSet>
                    <minimizeJar>true</minimizeJar>
                </configuration>
            </execution>
        </executions>
    </plugin>
    </plugins
</build>
```

Dependencies:
---

- **Bukkit API**
  * https://github.com/Bukkit/Bukkit
  * http://dl.bukkit.org/downloads/bukkit/
  * Required in order to power Platform.BUKKIT
- **Sponge API**
  * https://repo.spongepowered.org/maven/org/spongepowered/spongeapi/
- **Sponge server**
  * https://repo.spongepowered.org/maven/org/spongepowered/sponge/
  * Required in order to power Platform.SPONGE


Contact:
---

Nick at Nikolai.Kalashnikov@hotmail.com

Nicodemis79 on Skype


[http://rainbowcraft.net/](http://Rainbowcraft.net/ "Rainbowcraft")


Javadocs
---

~~[http://ci.battleplugins.com/job/Version/javadoc](http://ci.battleplugins.com/job/Version/javadoc "javadocs")~~

~~[http://rainbowcraft.sytes.net:8080/job/Version/javadoc](http://rainbowcraft.sytes.net:8080/job/Version/javadoc "javadocs")~~
