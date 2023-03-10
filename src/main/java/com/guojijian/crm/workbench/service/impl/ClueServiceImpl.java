package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.commons.contants.Contants;
import com.guojijian.crm.commons.util.DateUtils;
import com.guojijian.crm.commons.util.UUIDUtils;
import com.guojijian.crm.settings.pojo.User;
import com.guojijian.crm.workbench.mapper.ClueMapper;
import com.guojijian.crm.workbench.mapper.ContactsActivityRelationMapper;
import com.guojijian.crm.workbench.pojo.*;
import com.guojijian.crm.workbench.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private ClueRemarkService clueRemarkService;

    @Autowired
    private CustomerRemarkService customerRemarkService;

    @Autowired
    private ContactsRemarkService contactsRemarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ContactsActivityRelationService contactsActivityRelationService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRemarkService transactionRemarkService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    public List<Clue> queryConditionClue(Clue clue) {
        return clueMapper.selectConditionClue(clue);
    }

    public int saveClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    public int dropCluesById(String[] ids) {
        return clueMapper.deleteCluesById(ids);
    }

    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    public int alterClueById(Clue clue) {
        return clueMapper.updateClueById(clue);
    }

    public Clue queryClueDetailById(String id) {
        return clueMapper.selectClueDetailById(id);
    }

    public Clue queryClueInfoById(String id) {
        return clueMapper.selectClueById(id);
    }

    public Clue queryClueForConvertById(String id) {
        return clueMapper.selectClueForConvertById(id);
    }

    public int dropClueById(String id) {
        return clueMapper.deleteClueById(id);
    }

    public void saveConvert(Map<String, Object> map) {
        User user=(User) map.get(Contants.SESSION_USER);
        //????????????id???????????????????????????
        Clue clue=queryClueForConvertById((String)map.get("clueId"));
        //??????Customer?????????
        Customer customer=new Customer();
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setCreateBy(user.getId());
        customer.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        customer.setDescription(clue.getDescription());
        customer.setId(UUIDUtils.getUUID());
        customer.setName(clue.getCompany());
        customer.setNextContactDate(clue.getNextContactDate());
        customer.setOwner(clue.getOwner());
        customer.setContactSummary(clue.getContactSummary());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        customerService.saveCustomer(customer);
        //??????Contacts?????????
        Contacts contacts=new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getId());
        contacts.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        contacts.setCustomerId(customer.getId());
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setId(UUIDUtils.getUUID());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactDate(clue.getNextContactDate());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contactsService.saveContacts(contacts);
        //????????????id??????????????????
        List<ClueRemark> clueRemarkList=clueRemarkService.queryClueRemarkForConvertByClueId((String) map.get("clueId"));
        //????????????????????????
        if(clueRemarkList!=null && clueRemarkList.size()>0){
            //???????????????????????????
            List<CustomerRemark> customerRemarkList=new ArrayList<CustomerRemark>();
            CustomerRemark cr=null;
            //??????????????????????????????
            List<ContactsRemark> contactsRemarkList=new ArrayList<ContactsRemark>();
            ContactsRemark contactsRemark=null;
            for (ClueRemark clueRemark : clueRemarkList) {
                cr=new CustomerRemark();
                cr.setCreateBy(clueRemark.getCreateBy());
                cr.setCreateDate(clueRemark.getCreateDate());
                cr.setCustomerId(customer.getId());
                cr.setEditBy(clueRemark.getEditBy());
                cr.setEditDate(clueRemark.getEditDate());
                cr.setEditFlag(clueRemark.getEditFlag());
                cr.setId(UUIDUtils.getUUID());
                cr.setNoteContent(clueRemark.getNoteContent());
                customerRemarkList.add(cr);

                contactsRemark=new ContactsRemark();
                contactsRemark.setCreateBy(clueRemark.getCreateBy());
                contactsRemark.setCreateDate(clueRemark.getCreateDate());
                contactsRemark.setContactsId(contacts.getId());
                contactsRemark.setEditBy(clueRemark.getEditBy());
                contactsRemark.setEditDate(clueRemark.getEditDate());
                contactsRemark.setEditFlag(clueRemark.getEditFlag());
                contactsRemark.setId(UUIDUtils.getUUID());
                contactsRemark.setNoteContent(clueRemark.getNoteContent());
                contactsRemarkList.add(contactsRemark);
            }
            customerRemarkService.createCustomerRemarks(customerRemarkList);
            contactsRemarkService.createContactsRemarks(contactsRemarkList);
        }

        //??????????????????????????????????????????
        List<Activity> activityList=activityService.queryActivityDetailByClueId((String)map.get("clueId"));
        //??????????????????????????????
        if(activityList!=null && activityList.size()>0){
            //??????????????????????????????????????????????????????
            List<ContactsActivityRelation> contactsActivityRelationList=new ArrayList<ContactsActivityRelation>();
            ContactsActivityRelation car=null;
            for(Activity activity : activityList){
                car=new ContactsActivityRelation();
                car.setId(UUIDUtils.getUUID());
                car.setContactsId(contacts.getId());
                car.setActivityId(activity.getId());
                contactsActivityRelationList.add(car);
            }
            contactsActivityRelationService.createContactsActivityRelations(contactsActivityRelationList);
        }

        //??????????????????????????????
        if("true".equals((String) map.get("isCreateTran"))){
            //?????????????????????
            Transaction tran=new Transaction();
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
            tran.setCustomerId(customer.getId());
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setId(UUIDUtils.getUUID());
            tran.setMoney((String) map.get("money"));
            tran.setName((String) map.get("name"));
            tran.setOwner(user.getId());
            tran.setStage((String) map.get("stage"));

            transactionService.createTransaction(tran);

            //???????????????????????????
            List<TransactionRemark> trList=new ArrayList<TransactionRemark>();
            TransactionRemark tr=null;
            for(ClueRemark clueRemark : clueRemarkList){
                tr=new TransactionRemark();
                tr.setCreateBy(clueRemark.getCreateBy());
                tr.setCreateDate(clueRemark.getCreateDate());
                tr.setEditBy(clueRemark.getEditBy());
                tr.setEditDate(clueRemark.getEditDate());
                tr.setEditFlag(clueRemark.getEditFlag());
                tr.setId(UUIDUtils.getUUID());
                tr.setNoteContent(clueRemark.getNoteContent());
                tr.setTranId(tran.getId());
                trList.add(tr);
            }
            transactionRemarkService.insertTransactionRemarks(trList);
        }
        //????????????id?????????????????????
        clueRemarkService.dropClueRemarksByClueId((String) map.get("clueId"));
        //????????????id??????????????????????????????????????????
        clueActivityRelationService.dropClueActivityRelationsByClueId((String) map.get("clueId"));
        //????????????id????????????
        dropClueById((String) map.get("clueId"));
    }
}
