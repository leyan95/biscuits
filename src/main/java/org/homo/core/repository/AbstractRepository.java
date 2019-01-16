package org.homo.core.repository;

import org.homo.core.annotation.Repository;
import org.homo.core.model.BaseEntity;
import org.homo.pocket.session.SessionFactory;
import org.homo.pocket.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author wujianchuan 2018/12/26
 */
public abstract class AbstractRepository<T extends BaseEntity> implements HomoRepository<T> {
    @Autowired
    private ApplicationContext context;

    private RepositoryProxy<T> proxy;

    protected Session session;

    public void installSession() {
        Repository repository = this.getClass().getAnnotation(Repository.class);
        this.session = SessionFactory.getSession(repository.database());
    }

    public RepositoryProxy<T> getProxy() {
        if (this.proxy != null) {
            return this.proxy;
        } else {
            this.proxy = new RepositoryProxy<>();
            proxy.setRepository(context, this);
        }
        return this.proxy;
    }
}
