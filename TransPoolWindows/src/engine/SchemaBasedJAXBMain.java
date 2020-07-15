package engine;

public class SchemaBasedJAXBMain {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    /*public static void main(String[] args) {
        InputStream inputStream = SchemaBasedJAXBMain.class.getResourceAsStream("/resources/master.xml");
        try {
            TransPool transPool = deserializeFrom(inputStream);
            System.out.println("name of first country is: " + transPool.getPlannedTrips().getTransPoolTrip().toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    private static TransPool deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (TransPool) u.unmarshal(in);
    }*/
}
