package de.skuzzle.slf4j;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Automatically installs the {@link SLF4JBridgeHandler} upon start of the
 * servlet container in order to redirect JDK logging to slf4j. Additionally,
 * this class will modify the JDK's root logger's log level in order to redirect
 * messages of any severity to slf4j. You can specify this level using the
 * system property <code>{@value #JDK_LOG_LEVEL}</code>.
 * <p>
 * Please refer to the slf4j <a
 * href="http://www.slf4j.org/legacy.html#jul-to-slf4j">documentation</a> in
 * order to understand the implications of redirecting the JDK log to slf4j.
 *
 * @author Simon Taddiken
 * @see #JDK_LOG_LEVEL
 */
public final class BridgeHandlerInstaller implements ServletContainerInitializer {

    /**
     * System property for specifying the log level to which the JDK root logger
     * will be set. If not specified, the root logger's level will be set to
     * {@link Level#ALL} which will cause every single log message to be
     * redirected to slf4j.
     */
    public static final String JDK_LOG_LEVEL = "de.skuzzle.slf4j.jdkLogLevel";

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(
            BridgeHandlerInstaller.class);

    /**
     * Public constructor for Java's Service Loader.
     *
     * @deprecated Do not instantiate this class manually.
     */
    @Deprecated
    public BridgeHandlerInstaller() {
        // do not call manually
    }

    /**
     * Installs the bridge handler and sets the log level of the root logger.
     */
    public final void onStartup(Set<Class<?>> c, ServletContext ctx)
            throws ServletException {
        LOG.trace("Installing SLF4jBridgeHandler");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        final String lvlAsString = System.getProperty(JDK_LOG_LEVEL, "ALL");
        final Level logLevel = Level.parse(lvlAsString);
        LOG.trace("Setting JDK root logger to level '{}'", lvlAsString);
        Logger.getLogger("").setLevel(logLevel);
    }
}
