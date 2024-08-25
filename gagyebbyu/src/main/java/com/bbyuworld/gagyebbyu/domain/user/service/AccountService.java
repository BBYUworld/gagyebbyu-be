package com.bbyuworld.gagyebbyu.domain.user.service;

import com.bbyuworld.gagyebbyu.domain.asset.entity.Asset;
import com.bbyuworld.gagyebbyu.domain.asset.entity.AssetDeposit;
import com.bbyuworld.gagyebbyu.domain.asset.entity.Type;
import com.bbyuworld.gagyebbyu.domain.asset.repository.AssetDepositRepository;
import com.bbyuworld.gagyebbyu.domain.asset.repository.AssetRepository;
import com.bbyuworld.gagyebbyu.domain.user.dto.UserResponseDto;
import com.bbyuworld.gagyebbyu.domain.user.entity.User;
import com.bbyuworld.gagyebbyu.domain.user.repository.UserRepository;
import com.bbyuworld.gagyebbyu.global.api.asset.CreateDemandDepositAccountDto;
import com.bbyuworld.gagyebbyu.global.api.demanddeposit.AccountDto;
import com.bbyuworld.gagyebbyu.global.api.demanddeposit.DemandDepositDto;
import com.bbyuworld.gagyebbyu.global.util.ApiPost;
import com.bbyuworld.gagyebbyu.global.util.ApiResponse;
import com.bbyuworld.gagyebbyu.global.util.HeaderProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Value("${ssafy.api_key}")
    private String apiKey;
    private final ApiPost apiPost;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AssetDepositRepository assetDepositRepository;

    public List<DemandDepositDto> findAllDemandDeposit(){
        return sendPostAboutFindAllProducts();
    }
    public List<AccountDto> findAllUserAccount(Long userId){
        User user = userRepository.findUserById(userId);
        String userKey = user.getApiKey();
        List<AccountDto> list = sendPostAboutUserAccount(userKey);
        return list;
    }
    @Transactional
    public CreateDemandDepositAccountDto createUserAccount(Long userId, String uniqueNo, String bankName){
        User user = userRepository.findUserById(userId);
        String userKey = user.getApiKey();
        CreateDemandDepositAccountDto dto = sendPostAboutCreateUserAccount(userKey, uniqueNo);
        System.out.println("dto = "+dto);
        if(dto!= null ){
            Asset asset = Asset.builder()
                    .amount(0L)
                    .type(Type.DEPOSIT)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            user.getAssets().add(asset);
            asset.setUser(user);
            assetRepository.save(asset);
            AssetDeposit assetDeposit = AssetDeposit.builder()
                    .bank(bankName)
                    .bankCode(dto.getBankCode())
                    .number(dto.getAccountNo())
                    .type("수시입출금")
                    .amount(0L)
                    .hidden(false)
                    .build();
            assetDeposit.setAsset(asset);
            asset.setAssetDeposit(assetDeposit);
            assetRepository.save(asset);
            assetDepositRepository.save(assetDeposit);
            return dto;
        }
        return null;
    }
    private List<AccountDto> sendPostAboutUserAccount(String userKey){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccountList";
            String apiName = "inquireDemandDepositAccountList";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            ObjectNode headerNode = HeaderProvider.createHeaderNode(apiName, objectMapper, apiKey);
            headerNode.put("userKey", userKey);
            rootNode.set("Header", headerNode);
            String jsonBody = objectMapper.writeValueAsString(rootNode);
            System.out.println("Request Body: " + jsonBody);
            httpPost.setEntity(new StringEntity(jsonBody));

            try(CloseableHttpResponse response = client.execute(httpPost)){
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonNode responseRootNode = objectMapper.readTree(jsonResponse);
                JsonNode recNode = responseRootNode.get("REC");
                System.out.println("Response = "+jsonResponse);
                if (recNode.isArray()) {
                    return objectMapper.convertValue(recNode, new TypeReference<List<AccountDto>>() {});
                } else {
                    return new ArrayList<>();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private List<DemandDepositDto> sendPostAboutFindAllProducts(){
        System.out.println("Call Find All Products");
        try(CloseableHttpClient client = HttpClients.createDefault()){
            String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositList";
            String apiName = "inquireDemandDepositList";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            ObjectNode headerNode = HeaderProvider.createHeaderNode(apiName, objectMapper, apiKey);
            rootNode.set("Header", headerNode);
            String jsonBody = objectMapper.writeValueAsString(rootNode);
            System.out.println("Request Body: " + jsonBody);
            httpPost.setEntity(new StringEntity(jsonBody));

            try(CloseableHttpResponse response = client.execute(httpPost)){
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonNode responseRootNode = objectMapper.readTree(jsonResponse);
                JsonNode recNode = responseRootNode.get("REC");
                System.out.println("recNode = "+recNode);
                if (recNode.isArray()) {
                    return objectMapper.convertValue(recNode, new TypeReference<List<DemandDepositDto>>() {});
                } else {
                    return new ArrayList<>();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private CreateDemandDepositAccountDto sendPostAboutCreateUserAccount(String userKey, String uniqueNo){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/createDemandDepositAccount";
            String apiName = "createDemandDepositAccount";
            ObjectMapper mapper = new ObjectMapper();
            HttpPost httpPost = new HttpPost(url);
            ObjectNode rootNode = mapper.createObjectNode();
            ObjectNode headerNode = HeaderProvider.createHeaderNode(apiName, mapper, apiKey);
            headerNode.put("userKey", userKey);
            rootNode.set("Header", headerNode);
            rootNode.put("accountTypeUniqueNo", uniqueNo);
            httpPost.setHeader("Content-Type", "application/json");
            String jsonBody = mapper.writeValueAsString(rootNode);
            System.out.println("Request Body: " + jsonBody);
            httpPost.setEntity(new StringEntity(jsonBody));

            try(CloseableHttpResponse response = client.execute(httpPost)){
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonNode responseRootNode = mapper.readTree(jsonResponse);
                JsonNode recNode = responseRootNode.get("REC");
                if (recNode != null) {
                    CreateDemandDepositAccountDto dto = mapper.treeToValue(recNode, CreateDemandDepositAccountDto.class);
                    System.out.println("Converted DTO: " + dto);
                    return dto;
                } else {
                    System.out.println("REC node is null in the response");
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
