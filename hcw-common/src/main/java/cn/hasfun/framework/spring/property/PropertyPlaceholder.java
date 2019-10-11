package cn.hasfun.framework.spring.property;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {

    public PropertyPlaceholder(String[] propertyFileKeys) {
        List<Resource> resources = new ArrayList();
        String[] var3 = propertyFileKeys;
        int var4 = propertyFileKeys.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String propertyFile = var3[var5];
            String configProperties = System.getProperty(propertyFile);

            try {
                if (configProperties.startsWith("/")) {
                    resources.add(new FileSystemResource(configProperties));
                } else {
                    UrlResource configResource = new UrlResource(new URL(configProperties));
                    resources.add(configResource);
                }
            } catch (Exception var9) {
                throw new BeanCreationException("failed to load properties file '" + propertyFile + "'", var9);
            }
        }

        if (resources.size() > 0) {
            super.setLocations((Resource[])resources.toArray(new Resource[resources.size()]));
        }

    }
}
