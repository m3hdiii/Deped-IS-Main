package com.deped.repository;

import com.deped.model.account.AccountStatus;
import com.deped.model.account.Gender;
import com.deped.model.account.Position;
import com.deped.model.account.User;
import com.deped.model.brand.Brand;
import com.deped.model.brand.BrandModel;
import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.features.*;
import com.deped.model.location.City;
import com.deped.model.location.Continent;
import com.deped.model.location.Country;
import com.deped.model.location.office.Department;
import com.deped.model.location.office.Section;
import com.deped.model.order.Order;
import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsState;
import com.deped.model.order.Schedule;
import com.deped.model.request.Request;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsID;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.tracker.RequestTracker;
import com.deped.model.tracker.TrackingStatus;
import com.deped.model.unit.Unit;
import com.deped.repository.other.DashboardRepositoryImpl;
import com.deped.repository.utils.HibernateFacade;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Launcher {
    public static void main(String[] args) {

        new DashboardRepositoryImpl().getBasicInfo();
        //        fetchUserByUsername("mehdi");

        //requestDetails();

        //bindLeftAssociationFacadeMethod();
        //bindLeftAssociationFacadeMethod();
        //orderBindings();
        //OrderDetailsWithRequestTracker();

    }

    private static void fetchUserByUsername(String username) {
        Session hibernateSession = null;
        try {
            hibernateSession = HibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Transaction tx = null;

        List<User> rows;

        try {
            tx = hibernateSession.beginTransaction();
            Query<User> namedQuery = hibernateSession.createNamedQuery("SELECT user FROM User user WHERE user.username = :username", User.class);
            namedQuery.setParameter("uername", username);

            rows = namedQuery.list();
            tx.commit();
            System.out.println(rows);
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
        } finally {
//            if (hibernateSession != null)
//                hibernateSession.close();
        }

    }


    private static void requestDetails() {
        Session session = Launcher.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        NativeQuery<Request> nativeQuery = session.createNativeQuery("SELECT * FROM request WHERE request_id = 19", Request.class);
        List<Request> requests = nativeQuery.list();
        tr.commit();
        ObjectMapper mapper = new ObjectMapper();

        //For testing

        try {
            //Convert object to JSON string and save into file directly

            //Convert object to JSON string
            String jsonInString = mapper.writeValueAsString(requests.get(0));
            System.out.println(jsonInString);


        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void OrderDetailsWithRequestTracker() {
        Session session = Launcher.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        session.get(User.class, 13L);
        User user = createUser();
        session.save("User", user);

        Item equipment = createEquipment();
        session.save("Equipment", equipment);

        ItemDetails it1 = createItemDetailsForEquipment1();
        it1.setItem(equipment);
        session.save("ItemDetails", it1);

        Unit pack = createUnit();
//        unit.setOrderDetailsList();
        session.save("Pack", pack);

        Category category = createCategory();
//        category.setOrderDetailsSet();
        session.save("Category", category);

        Request request = new Request();
        request.setRequestDate(new Date());
        request.setUser(user);
        request.setUserMessage("I need these equipments");

        session.save("Request", request);

        RequestDetails requestDetails = new RequestDetails();
        requestDetails.setItem(equipment);
        requestDetails.setRequest(request);
        requestDetails.setRequestDetailsStatus(RequestDetailsStatus.WAITING);
        requestDetails.setRequestQuantity(100);
        requestDetails.setRequestDetailsID(new RequestDetailsID(request.getRequestId(), equipment.getName()));
        session.save("RequestDetails", requestDetails);

        Brand brand = createBrand();
        session.save("Brand", brand);

        BrandModel brandModel1 = createBrandModel1(brand);
        BrandModel brandModel2 = createBrandModel2(brand);

        session.save("BrandModel", brandModel1);
        session.save("BrandModel", brandModel2);


        brand.setBrandModels(new ArrayList<BrandModel>() {{
            add(brandModel1);
            add(brandModel2);
        }});

        ItemDetails itemDetails1 = createItemDetailsForEquipment1();
        itemDetails1.setBrandModel(brandModel1);

        brandModel1.setItemDetails(new ArrayList<ItemDetails>() {{
            add(itemDetails1);
        }});

        itemDetails1.setItem(equipment);
        session.save("ItemDetails", itemDetails1);


        RequestTracker requestTracker = new RequestTracker();
        requestTracker.setRequestDetails(requestDetails);
        requestTracker.setTrackingStatus(TrackingStatus.IN_USE);
        requestTracker.setItemDetails(itemDetails1);
        session.save("RequestTracker", requestTracker);


        RequestTracker requestTracker2 = new RequestTracker();
        requestTracker2.setRequestDetails(requestDetails);
        requestTracker2.setTrackingStatus(TrackingStatus.IN_USE);
        requestTracker2.setItemDetails(itemDetails1);
        session.save("RequestTracker", requestTracker2);


        tr.commit();
    }

    private static void OrderBindings() {
        Session session = Launcher.getSessionFactory().getCurrentSession();

        Transaction tr = session.beginTransaction();
        User user = createUser();
        session.save("User", user);

        Item equipment = createEquipment();
        session.save("Equipment", equipment);

        ItemDetails it1 = createItemDetailsForEquipment1();
        it1.setItem(equipment);
        session.save("ItemDetails", it1);

        Unit unit = createUnit();
//        unit.setOrderDetailsList();
        session.save("unit", unit);

        Category category = createCategory();
//        category.setOrderDetailsSet();
        session.save("Category", category);

        Order order = createOrder();
//        order.setDeliveryInformation();
        order.setUser(user);
//        order.setOrderDetails();
        session.save("Order", order);


        OrderDetails details = createOrderDetails();
        details.setItem(equipment);
        details.setOrder(order);
        details.setCategory(category);
        details.setUnit(unit);
        session.save(details);

        tr.commit();
    }


    private static OrderDetails createOrderDetails() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderDetailsState(OrderDetailsState.WAITING);
        //FIXME
//        orderDetails.setPackCapacity(12);

        orderDetails.setTotalQuantityRequestNo(50);
        orderDetails.setUnitPrice(190.0);

        return orderDetails;
    }

    private static Order createOrder() {
        Order order = new Order();
        order.setBudgetAmount(10000.0);
        order.setOrderDate(new Date());
        order.setRequiredDate(new Date());
        order.setOrderSchedule(Schedule.FIRST_QUARTER);
        return order;
    }

    private static Unit createUnit() {
        Unit pack = new Unit();
        pack.setCreationDate(new Date());
        pack.setDescription("Box Description");
        pack.setName("Pack");
        //FIXME unit capacity
        return pack;
    }

    private static Category createCategory() {
        Category category = new Category();
        category.setCreationDate(new Date());
        category.setDescription("Common Office Supply Description");
        category.setName("Common Office Supply");
        return category;
    }

    private static void bindLeftAssociationFacadeMethod2() {
        Session session = Launcher.getSessionFactory().openSession();

        Transaction tr = session.beginTransaction();

        Item equipment = createEquipment();
        session.save("Equipment", equipment);

        Item semiExpendable = createSemiExpandable();
        session.save("SemiExpendable", semiExpendable);

        Item goods = createGoods();
        session.save("Goods", goods);

        Brand brand = createBrand();
        session.save("Brand", brand);

        BrandModel brandModel1 = createBrandModel1(brand);
        BrandModel brandModel2 = createBrandModel2(brand);

        session.save("BrandModel", brandModel1);
        session.save("BrandModel", brandModel2);


        brand.setBrandModels(new ArrayList<BrandModel>() {{
            add(brandModel1);
            add(brandModel2);
        }});

        ItemDetails itemDetails1 = createItemDetailsForEquipment1();
        ItemDetails itemDetails2 = createItemDetailsForEquipment2();
        ItemDetails itemDetails3 = createItemDetailsForSemiExpendable1();
        ItemDetails itemDetails4 = createItemDetailsForSemiExpendable2();

        itemDetails1.setBrandModel(brandModel1);
        itemDetails2.setBrandModel(brandModel2);

        brandModel1.setItemDetails(new ArrayList<ItemDetails>() {{
            add(itemDetails1);
            add(itemDetails2);
        }});

        itemDetails1.setItem(equipment);
        itemDetails2.setItem(equipment);

        itemDetails3.setItem(semiExpendable);
        itemDetails4.setItem(semiExpendable);

        session.save("ItemDetails", itemDetails1);
        session.save("ItemDetails", itemDetails2);
        session.save("ItemDetails", itemDetails3);
        session.save("ItemDetails", itemDetails4);

        equipment.setItemDetails(new ArrayList<ItemDetails>() {{
            add(itemDetails1);
            add(itemDetails2);
        }});

        semiExpendable.setItemDetails(new ArrayList<ItemDetails>() {{
            add(itemDetails3);
            add(itemDetails4);
        }});


        tr.commit();

    }

    private static ItemDetails createItemDetailsForEquipment1() {
        ItemDetails id = new ItemDetails();
        id.setColour(Colour.BLACK);
        id.setCondition(Condition.OK);
        id.setCreationDate(new Date());
        id.setEquipmentAvailability(EquipmentAvailability.AVAILABLE);
        id.setEquipmentSerialNo("xyz-xyz-12-68");
        id.setLifeSpan((short) 2);
        id.setMaterial(Material.METAL);
        id.setPurchasePrice(192.0);
        id.setWeightInGram(1000L);
        id.setOfficeSerialNo("office-sn-090182011");
        return id;
    }

    private static ItemDetails createItemDetailsForEquipment2() {
        ItemDetails id = new ItemDetails();
        id.setColour(Colour.YELLOW);
        id.setCondition(Condition.DAMAGED);
        id.setCreationDate(new Date());
        id.setEquipmentAvailability(EquipmentAvailability.AVAILABLE);
        id.setEquipmentSerialNo("xyz-xyz-12-69");
        id.setLifeSpan((short) 3);
        id.setMaterial(Material.MONOBLOCK);
        id.setPurchasePrice(2000.0);
        id.setWeightInGram(1600L);
        id.setOfficeSerialNo("office-sn-090182012");

        return id;
    }

    private static ItemDetails createItemDetailsForSemiExpendable1() {
        ItemDetails id = new ItemDetails();
        id.setColour(Colour.WHITE);
        id.setCreationDate(new Date());
        id.setLifeSpan((short) 2);
        id.setMaterial(Material.METAL);
        id.setPurchasePrice(50.0);
        id.setWeightInGram(100L);
        id.setOfficeSerialNo("office-sn-popsop");
        return id;
    }

    private static ItemDetails createItemDetailsForSemiExpendable2() {
        ItemDetails id = new ItemDetails();
        id.setColour(Colour.RED);
        id.setCreationDate(new Date());
        id.setLifeSpan((short) 8);
        id.setMaterial(Material.WOOD);
        id.setPurchasePrice(10.0);
        id.setWeightInGram(10L);
        id.setOfficeSerialNo("office-sn-tooti");
        return id;
    }

    private static BrandModel createBrandModel1(Brand brand) {
        BrandModel brandModel1 = new BrandModel();
        brandModel1.setModelNumber("DX-SHOCK 5");
        brandModel1.setSpecification("Light - Beautiful - Multi Colour");
        brandModel1.setDescription("Good For Women between 25 till 45");
        brandModel1.setBrand(brand);
        brandModel1.setPicUrl("/brand-model/model-dx-shock.png");
        return brandModel1;
    }

    private static BrandModel createBrandModel2(Brand brand) {
        BrandModel brandModel2 = new BrandModel();
        brandModel2.setModelNumber("SKY POWER");
        brandModel2.setSpecification("Nice Design - Multi Colour - Special Price");
        brandModel2.setDescription("Good For Basketball Players");
        brandModel2.setBrand(brand);
        brandModel2.setPicUrl("/brand-model/model-sky-power.png");
        return brandModel2;
    }


    private static Brand createBrand() {
        Brand brand = new Brand();
        brand.setCreationDate(new Date());
        brand.setName("NIKE");
        brand.setDescription("NIKE brand Description");
        brand.setLogoUrl("/brand/nike.png");
        return brand;
    }

    private static Item createGoods() {
        Item goods = new Item();
        goods.setName("Semi Expendable 1");
        goods.setDescription("Semi Expendable Description");
        goods.setThreshold(30);
        goods.setQuantity(100);
        goods.setCreationDate(new Date());
        goods.setPicName("/items/goods/goods1.png");
        return goods;
    }

    private static Item createSemiExpandable() {
        Item se = new Item();
        se.setName("Semi Expendable 1");
        se.setDescription("Semi Expendable Description");
        se.setFunctionType(FunctionType.NON_ELECTRICAL);
        se.setThreshold(30);
        se.setQuantity(100);
//        se.setLifeSpan((short)1);
        se.setCreationDate(new Date());
        se.setPicName("/items/semi-expandable/semi-expandable1.png");
        return se;
    }

    private static Item createEquipment() {
        Item eq = new Item();
        eq.setName("Equipment 1");
        eq.setDescription("Equipment Description");
        eq.setFunctionType(FunctionType.ELECTRICAL);
        eq.setThreshold(20);
        eq.setQuantity(50);
//        eq.setMaterial(Material.METAL);
//        eq.setWeightInGram(1500);
//        eq.setLifeSpan((short)6);
        eq.setCreationDate(new Date());
        eq.setPicName("/items/equipment/equipment1.png");

        return eq;
    }

    private static void bindLeftAssociationFacadeMethod() {
        Session session = Launcher.getSessionFactory().getCurrentSession();

        Transaction tr = session.beginTransaction();
        Department department = createDepartment();
        session.save("Department", department);

        for (int i = 0; i < 7; i++) {
            Section section = createSection();
            section.setDepartment(department);
            session.save("Section", section);
        }

//        Country country = createCountry();
//        session.save("Country", country);
//
//        City city = createCity();
//        city.setCountry(country);
//        country.setCities(new ArrayList<City>() {{
//            add(city);
//        }});
//        session.save("City", city);
//
//        country.setCapital(city);


//        User user = createUser();
////        user.setSection(section);
//        user.setCityOfBorn(city);
//        session.save("User", user);

//        Request request = createRequest();
//        request.setUser(user);
//
//        session.save("Request", request);

        tr.commit();
    }

    private static Request createRequest() {
        Request req = new Request();
        req.setRequestDate(new Date());
        req.setUserMessage("For The IT Section");
        return req;
    }

    private static Section createSection() {
        Section section = new Section();
        section.setName(UUID.randomUUID().toString().substring(0, 5));
        section.setCreationDate(new Date());
        section.setDescription(UUID.randomUUID().toString().substring(0, 20));
        return section;
    }

    private static Department createDepartment() {
        Department department = new Department();
        department.setCreationDate(new Date());
        department.setDepartmentHead("Joseph Raphael");
        department.setDescription("Department of Science Description");
        department.setName("Department of Science");

        return department;
    }

    public static User createUser() {
        User user = new User();
        user.setFirstName("Mehdi");
        user.setLastName("Afsari");
        user.setMiddleName("NON");
        user.setUsername("Mehdi");
        user.setPassword("123");
        user.setEmailAddress("ix56@yahoo.com");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setAddress("Engineers Hill");
        user.setAge(32);
        user.setCreationDate(new Date());
        user.setEmploymentDate(new Date());
        user.setGender(Gender.MALE);
        user.setPhoneNo1("09062658383");
        user.setPhoneNo2("09272658383");
        user.setPosition(Position.HEAD);
        user.setReferrerAddress("Bakakeng");
        user.setReferrerName("Kieth Christian");
        user.setReferrerPhoneNo1("09659898989");
        user.setReferrerPhoneNo2("09643232323");
        user.setWebsite("http://www.google.com");
        user.setBirthDate(new Date());
        user.setPicUrl("/user/user1.png");
        //request and order here

        return user;
    }

    private static Country createCountry() {

        Country country = new Country();
        country.setContinent(Continent.AFRICA);
        country.setGdp(100.0);
        country.setCountryCode("IRN");
        country.setGovernmentForm("Monarchy");
        country.setHeadOfState("Hassan Rouhani");
        country.setIndependentYear((short) 100);
        country.setLifeExpectancy(50.0);
        country.setLocalName("Iran");
        country.setSurfaceArea(1000000.0);
        country.setCode2("IR");
        country.setRegion("South Africa");
        country.setOldGdp(800.0);
        country.setName("IRAN");
        return country;
    }

    private static City createCity() {

        City city = new City();
        city.setDistrict("Red District");
        city.setName("Tehran");
        city.setPopulation(17000000L);
        return city;

    }

    private static volatile SessionFactory sessionInstance;

    private Launcher() {
    }

    public static SessionFactory getSessionFactory() {
        return HibernateFacade.getSessionFactory();
    }
}
