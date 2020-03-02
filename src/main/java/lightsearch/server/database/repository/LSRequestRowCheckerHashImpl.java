/*
 *  Copyright 2019 ViiSE.
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
 */

package lightsearch.server.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component("lsRequestRowCheckerHash")
public class LSRequestRowCheckerHashImpl implements LSRequestRowChecker<String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isExist(String lsCode) {
        try {
            jdbcTemplate.setQueryTimeout(30);
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT count(*) FROM LS_REQUEST WHERE lsCode = ?",
                    new Object[]{lsCode.getBytes("windows-1251")}, Integer.class);

            if (count != null) {
                if (count > 1)
                    return false;
                return count != 1;
            } else
                return false;
        } catch (UnsupportedEncodingException ignore) {}

        return false;
    }
}
