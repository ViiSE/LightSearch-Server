/*
 *  Copyright 2020 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package lightsearch.server.producer.entity;

import lightsearch.server.entity.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("productProducerDefault")
public class ProductProducerDefaultImpl implements ProductProducer {

    private final ApplicationContext ctx;

    public ProductProducerDefaultImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Product getProductSimpleInstance(String id) {
        return (Product) ctx.getBean("productSimple", id);
    }

    @Override
    public Product getProductWithAmountInstance(Product product, float amount) {
        return (Product) ctx.getBean("productWithAmount", product, amount);
    }

    @Override
    public Product getProductWithNameInstance(Product product, String name) {
        return (Product) ctx.getBean("productWithName", product, name);
    }

    @Override
    public Product getProductWithSubdivisionInstance(Product product, String subdivision) {
        return (Product) ctx.getBean("productWithSubdivision", product, subdivision);
    }
}
