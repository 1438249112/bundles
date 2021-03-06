package osgi.executor.provider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.ConfigurationPolicy;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;
import aQute.bnd.annotation.metatype.Configurable;

/**
 * This bundle provides a java.util.concurrent.Executor service that can be
 * configured and is shared between all bundles. It is registered as a private
 * service that is then used between ExecutoreSubmitter services. This
 * indirection is used so we can throttle bundles.
 */
@Component(immediate = true, provide = ExecutorImpl.class, designate = Configuration.class, name = "osgi.executor.provider", configurationPolicy = ConfigurationPolicy.optional)
public class ExecutorImpl {
	ExecutorService			es;
	BlockingQueue<Runnable>	queue	= new LinkedBlockingQueue<Runnable>();
	Logger					log;

	/*
	 * Creates a new instance of the underlying implementation of the executor
	 * service (depending on the configuration parameters) if needed, or returns
	 * a pre-existing instance of this service, shared by all bundles.
	 * @param properties Configuration parameters, passed by the framework
	 */
	@Activate
	void activate(Map<String,Object> properties) {

		Configuration config = Configurable.createConfigurable(Configuration.class, properties);
		int coreSize = config.coreSize();
		int maxSize = config.maximumPoolSize();
		long keepAlive = config.keepAliveTime();
		int cores = Runtime.getRuntime().availableProcessors();

		if (coreSize < 10 * cores)
			coreSize = 30;

		if (maxSize <= coreSize)
			maxSize = coreSize * 4;

		if (keepAlive < 10 || keepAlive > 1000)
			keepAlive = 300;

		es = new ThreadPoolExecutor(coreSize, maxSize, keepAlive, TimeUnit.SECONDS, queue, //
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/*
	 * Cancels the tasks submitted by the exiting bundle, shutting down the
	 * executor service if no more bundle is using it
	 */
	@Deactivate
	void deactivate() {
		List<Runnable> running = es.shutdownNow();
		if (!running.isEmpty())
			log.warn("Shutting down while tasks %s are running", running);
	}

	/*
	 * Execute a runnable
	 */
	public void execute(Runnable command) {
		es.submit(command);
	}

	/*
	 * Reference to the log
	 */
	@Reference
	void setLogger(Logger log) {
		this.log = log;
	}

}