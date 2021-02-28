## Spring Data Jpa와  Jooq를 같이 사용하기

Spring Data JPA와 QueryDSL을 이용해서 몇 번의 프로젝트를 진행 해 보았는데  
별도의 summary 테이블이 없는 환경에서 두 세개 이상의 테이블에 대한 조인이 들어가고  
페이지네이션까지 요구되는 쿼리는 정말 QueryDSL로 작성하기가 힘들다고 생각이 들어왔다.  

예를 들어 group_concat이 필요한 상황에서 페이지네이션이 필요하다면 QueryDSL 환경에선   
먼저 페이지네이션을하고 결과에 리스트에 대해 group_concat 된 결과를 다시 조회하여 조합하는게 더 편리했다.  

ex) 사용자에 할당 된 Role 목록을 group_concat 형태로 보여줘야 한다면  
select u from User; -> pagination  
select ur from UserRole where ur.user_key in (...); -> pagination 된 결과에 대해서 재조회 

하지만 group_concat 된 항목에 대해 검색 조건이 들어가야 한다면 또 다른 이야기다.  
결국 Native query를 사용해야 했는데 orm.xml 등에 네이티브 쿼리를 작성하고 관리하는 것도 한계가 있어  
마이바티스를 같이 사용했다.  

Spring Boot를 잘 활용하고자 로컬에선 어떠한 설정 없이 바로 프로젝트를 돌려 볼 수 있게 H2를 사용하고  
실제 서비스 환경에선 MariaDB를 사용했는데 group_concat을 사용한 코드가 여기서 또 발목을 잡았다.  

아래와 같이 짠 쿼리가 h2에선 제대로 동작하지 않았다.
```sql
SELECT R.REPORT_KEY
    , R.FORMATTED_REPORT_ID REPORT_ID
    , RM.SENDER_EMAIL
    , RM.SENT_AT
    , GROUP_CONCAT(RMR.USER_EMAIL ORDER BY RMR.USER_EMAIL SEPARATOR ', ') RECEIVERS
    , R.SITE_NAME
    , R.AE_NAME
    , CONCAT_WS(' ',
              SD.DRUG_NAME,
              IF(SD.DRUG_DOSE = '', NULL, SD.DRUG_DOSE),
              IF(SD.DRUG_UNIT = '', NULL, SD.DRUG_UNIT),
              IF(SD.WHO_DRUG_CODE = '', NULL, SD.WHO_DRUG_CODE)
    ) DRUG_NAME
    , RM.STATUS
FROM SPO_DRUG SD
```
H2는 아래와 같이 작성해주어야 제대로 동작했다. 
```sql
SELECT R.REPORT_KEY
    , R.FORMATTED_REPORT_ID REPORT_ID
    , RM.SENDER_EMAIL
    , RM.SENT_AT
    , LISTAGG(RMR.USER_EMAIL ', ') WITHIN GROUP (ORDER BY RMR.USER_EMAIL) RECEIVERS
    , R.SITE_NAME
    , R.AE_NAME
    , R.DRUG_NAME
    , RM.STATUS
FROM SPO_DRUG SD
```  
프로젝트는 끝났지만 로컬 환경 때문에 동일한 select 쿼리를 dbms마다 나누어 작성해야 했던 부분이 가장 찝찝했다.  

JOOQ는 Type safe한 SQL을 작성하도록 도와주는 도구이다.  
Hibernate와 마찬가지로 애플리케이션 시작 시에 Schema가 일치하는지 validation도 가능하다.  
QueryDSL과 마찬가지로 메이븐과 같은 빌드 도구를 통해서 코드제네레이터를 사용하면 자바코드를 통해 쿼리도 작성 할 수 있다.  

아래는 코드제네레이터 설정이다.  
```xml
 <plugin>
    <groupId>org.jooq</groupId>
    <artifactId>jooq-codegen-maven</artifactId>
    <version>3.14.4</version>
    <!-- The plugin should hook into the generate goal -->
    <executions>

        <!-- case1 코드 제너레이터 -->
        <execution>
            <id>case1</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <configurationFile>src/main/resources/jooq/config.xml</configurationFile>
            </configuration>
        </execution>
    </executions>
</plugin>
```  
메이븐에서 지정한 config 파일은 아래와 같다.  
jdbc 접속 정볼르 통해서 코드를 제네레이션 할 수 있다.  
몇 가지 아쉬운 것은 H2의 한계라 그런지 컬럼이 몇개 빠진다거나 아예 생성이 되지 않은 경우가 있었다는 것.  
설정값들에 대해서 조금 삽질을 많이 했는데 나중에 본격적으로 프로젝트에서 사용해야 하는 때가 오면 더 자세히 파보아야 할 것 같다.  
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<configuration>
    <!-- JDBC connection parameters -->
    <jdbc>
        <driver>org.h2.Driver</driver>
        <url>jdbc:h2:tcp://localhost:9090/~/test</url>
        <user>sa</user>
        <password></password>
    </jdbc>

    <!-- Generator parameters -->
    <generator>
        <name>org.jooq.codegen.JavaGenerator</name>
        <database>
            <name>org.jooq.meta.h2.H2Database</name>
            <includes>HOM_USER</includes>
            <excludes></excludes>
            <inputSchema>PUBLIC</inputSchema>
        </database>
        <target>
            <packageName>me.taesu.springjooq</packageName>
            <directory>target/generated-sources/jooq</directory>
        </target>
        <generate>
            <indexes>true</indexes>
            <relations>true</relations>
            <javaTimeTypes>true</javaTimeTypes>
            <fluentSetters>true</fluentSetters>
            <!--<deprecated>true</deprecated>-->
            <!--<instanceFields>true</instanceFields>-->
            <!--<generatedAnnotation>true</generatedAnnotation>-->
            <!--<records>true</records>-->
<!--            <pojos>true</pojos>-->
            <!--<immutablePojos>false</immutablePojos>-->
            <!--<interfaces>false</interfaces>-->
            <!--<daos>true</daos>-->
            <!--<jpaAnnotations>true</jpaAnnotations>-->
            <!--<validationAnnotations>false</validationAnnotations>-->
            <!--<globalObjectReferences>true</globalObjectReferences>-->
            <!--<fluentSetters>true</fluentSetters>-->
        </generate>
    </generator>
    <onError>LOG</onError>
</configuration>
```

mvn generate를 수행하면 아래와 같이 target/generated-sources/jooq 디렉터리 하위 클래스들이 생성된다.  
<img src="https://github.com/dlxotn216/image/blob/master/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-28%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2012.08.24.png?raw=true" />

그럼 우리는 아래와 같이 QueryDSL과 비슷하게 쿼리를 작성할 수 있다.  
```java
final var mstUserRecords = dslContext
        .selectFrom(Tables.HOM_USER)
        .fetchInto(HomUserRecord.class);
//
//
return mstUserRecords.stream()
                     .map(record -> new UserSelectQuery(
                             record.getUserKey(),
                             record.getUserName(),
                             record.getUserEmail()
                     ))
                     .collect(Collectors.toList());

```  
JOOQ는 ORM이 아니다. 따라서 여러 ORM 프레임워크에서 당연히 지원해주던 쓰기지연, Lazy loading, 1차 캐시 등의  
장점은 누릴 수 없다. 하지만 Lazy loading으로 인한 N+1 문제도 신경쓸 필요가 없으니 장단이 있다고 생각한다.  

고민인 것은 ORM과 같이 사용 가능할까에 대한 부부인데 CQRS 패턴을 사용하여 명령과 조회를 나누고  
명령은 Hibernate와 같은 ORM을 활용하고 조회는 JOOQ를 사용한 네이티브 쿼리를 활용하면 어떨까 싶다.  
추후 서비스 분리에도 유리할 것 같고 말이다.  

> JOOQ로 작성된 쿼리를 Data Grip 같은 tool에서 손쉽게 돌릴 수 있을까?
> 실행 계획 등을 보려면 DBMS에 맞는 실제 쿼리가 필요 할텐데 말이다..   


### References
* https://www.baeldung.com/jooq-with-spring
* https://www.baeldung.com/spring-boot-access-h2-database-multiple-apps
* https://www.baeldung.com/jooq-with-spring
* https://medium.com/@tejozarkar/configure-jooq-with-spring-boot-and-postgresql-c362e41722b9
* https://www.jooq.org/doc/3.11/manual/code-generation/codegen-configuration/
* https://blog.naver.com/jasuil/221424703048
* https://stackoverflow.com/questions/59437902/spring-boot-jooq-integration-compilation-issue
* https://www.theteams.kr/teams/6045/post/69806
* https://www.python2.net/questions-778356.htm
* https://if.kakao.com/session/83