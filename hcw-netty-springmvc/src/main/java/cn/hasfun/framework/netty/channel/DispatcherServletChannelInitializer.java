package cn.hasfun.framework.netty.channel;

import cn.hasfun.framework.netty.handle.ServletNettyHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class DispatcherServletChannelInitializer extends ChannelInitializer<SocketChannel> {

    private DispatcherServlet dispatcherServlet;

    public DispatcherServletChannelInitializer(ApplicationContext applicationContext) throws Exception {

        MockServletContext servletContext = new MockServletContext();
        MockServletConfig servletConfig = new MockServletConfig(servletContext);
        AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        wac.setServletContext(servletContext);
        wac.setServletConfig(servletConfig);
        wac.setParent(applicationContext);
        wac.refresh();
        dispatcherServlet = new DispatcherServlet(wac);
        dispatcherServlet.init(servletConfig);
    }

    @Override
    public void initChannel(SocketChannel channel) throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline pipeline = channel.pipeline();

        // Uncomment the following line if you want HTTPS
        //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
        //engine.setUseClientMode(false);
        //pipeline.addLast("ssl", new SslHandler(engine));

        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
        pipeline.addLast("handler", new ServletNettyHandler(this.dispatcherServlet));
    }

}
