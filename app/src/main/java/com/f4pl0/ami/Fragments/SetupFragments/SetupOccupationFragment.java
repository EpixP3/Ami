package com.f4pl0.ami.Fragments.SetupFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;

public class SetupOccupationFragment extends Fragment {
    AutoCompleteTextView editOccupation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and store it in a variable
        View fragmentView = inflater.inflate(R.layout.fragment_setup_occupation, container, false);

        // Initiate components
        editOccupation = fragmentView.findViewById(R.id.editOccupation);
        editOccupation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((SetupActivity) getActivity()).setOccupation(s.toString());
            }
        });

        // Set occupations suggestions
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, occupationList.split("\n"));
        editOccupation.setAdapter(adapter);
        editOccupation.setThreshold(1);

        // Set the fragment view
        return fragmentView;
    }

    // List of all occupations
    String occupationList = "Abattoir worker\n" +
            "Aboriginal and Torres Strait Islander health worker\n" +
            "Accountant (general)\n" +
            "Accounts clerk\n" +
            "Actor\n" +
            "Actuary\n" +
            "Acupuncturist\n" +
            "Advertising specialist\n" +
            "Aeronautical engineer\n" +
            "Aeroplane pilot\n" +
            "Aged care worker\n" +
            "Agricultural consultant\n" +
            "Student\n" +
            "Agricultural engineer\n" +
            "Agricultural scientist\n" +
            "Air combat officer\n" +
            "Air force - general entrant\n" +
            "Air force technician/tradesperson\n" +
            "Air traffic controller\n" +
            "Air-conditioning and refrigeration mechanic\n" +
            "Aircraft baggage handler and airline ground crew\n" +
            "Aircraft maintenance engineer (avionics)\n" +
            "Aircraft maintenance engineer (mechanical)\n" +
            "Aircraft maintenance engineer (structural)\n" +
            "Alarm, security or surveillance monitor\n" +
            "Ambulance officer\n" +
            "Anaesthetist\n" +
            "Animal attendants and trainers\n" +
            "Aquaculture farmer\n" +
            "Arborist\n" +
            "Archaeologist\n" +
            "Architect\n" +
            "Architectural draftsperson\n" +
            "Archivist\n" +
            "Army officer\n" +
            "Army Soldier - Technician\n" +
            "Art director (film, television, stage)\n" +
            "Art teacher\n" +
            "Auctioneer\n" +
            "Audiologist\n" +
            "Audiovisual technician\n" +
            "Auditor (external and internal)\n" +
            "Author\n" +
            "Automotive electrician\n" +
            "Baker\n" +
            "Bank worker\n" +
            "Bar attendant\n" +
            "Barista\n" +
            "Barrister\n" +
            "Beauty therapist\n" +
            "Beef cattle farmer\n" +
            "Bicycle mechanic\n" +
            "Binder and finisher\n" +
            "Biomedical engineer\n" +
            "Biosecurity officer\n" +
            "Biotechnologist\n" +
            "Boat builder and repairer\n" +
            "Body artist or tattooist\n" +
            "Boiler or engine operator\n" +
            "Book or script editor\n" +
            "Bookkeeper\n" +
            "Bookmaker\n" +
            "Botanist\n" +
            "Brewer\n" +
            "Bricklayer\n" +
            "Broadcast transmitter operator\n" +
            "Builder's Labourer\n" +
            "Building insulation installer\n" +
            "Building surveyor\n" +
            "Bus driver\n" +
            "Business machine mechanic\n" +
            "Butcher or smallgoods maker\n" +
            "Cabinetmaker\n" +
            "Cafe or restaurant manager\n" +
            "Call or contact centre operator\n" +
            "Call or contact centre team leader\n" +
            "Camera operator (film, television or video)\n" +
            "Canvas goods fabricator\n" +
            "Car park attendant\n" +
            "Cardiologist\n" +
            "Cardiothoracic surgeon\n" +
            "Careers counsellor\n" +
            "Carpenter\n" +
            "Cartographer\n" +
            "Chef\n" +
            "Chemical engineer\n" +
            "Chemist\n" +
            "Chief executive or managing director\n" +
            "Child care centre manager\n" +
            "Child protection worker\n" +
            "Chiropractor\n" +
            "Cinema or theatre manager\n" +
            "Civil celebrant\n" +
            "Civil engineer\n" +
            "Civil engineering draftsperson\n" +
            "Civil engineering technician\n" +
            "Clay, concrete, glass or stone machine operator\n" +
            "Clinical haematologist\n" +
            "Clinical psychologist\n" +
            "Clothing production worker\n" +
            "Coastal engineer\n" +
            "Commercial cleaner\n" +
            "Commercial housekeeper (hotel/motel room attendant)\n" +
            "Commodities trader\n" +
            "Communications operator\n" +
            "Community corrections officer\n" +
            "Community worker\n" +
            "Computer engineer\n" +
            "Computer-aided design (CAD) technician\n" +
            "Concierge\n" +
            "Concreter\n" +
            "Confectionery maker\n" +
            "Conference and event organiser\n" +
            "Conservation Officer\n" +
            "Conservator\n" +
            "Construction estimator\n" +
            "Construction project manager\n" +
            "Construction rigger\n" +
            "Contract Administrator\n" +
            "Cook\n" +
            "Copywriter\n" +
            "Corporate general manager\n" +
            "Counsellor\n" +
            "Court bailiff or sheriff (Aus)\n" +
            "Coxswain\n" +
            "Crane chaser (dogger)\n" +
            "Crane, hoist or lift operator\n" +
            "Customer service manager\n" +
            "Customs officer (Border Force officer)\n" +
            "Dairy cattle farmer\n" +
            "Dancer or choreographer\n" +
            "Debt collector\n" +
            "Deck hand\n" +
            "Delivery driver\n" +
            "Dental assistant\n" +
            "Dental hygienist\n" +
            "Dental specialist\n" +
            "Dental technician\n" +
            "Dental therapist\n" +
            "Dentist\n" +
            "Dermatologist\n" +
            "Developer programmer\n" +
            "Diesel motor mechanic\n" +
            "Dietitian\n" +
            "Director (film, television, radio or stage)\n" +
            "Disability services officer\n" +
            "Disability worker\n" +
            "Diver\n" +
            "Diversional therapist\n" +
            "Dog handler or trainer\n" +
            "Domestic cleaner\n" +
            "Doorperson or luggage porter\n" +
            "Driller\n" +
            "Drilling engineer\n" +
            "Driving instructor\n" +
            "Drug and alcohol counsellor\n" +
            "Drycleaner\n" +
            "Early childhood (pre-primary school) teacher\n" +
            "Early childhood educator\n" +
            "Earth science technician\n" +
            "Earthmoving plant operator\n" +
            "Economist\n" +
            "Electorate officer\n" +
            "Electrical engineer\n" +
            "Electrical engineering draftsperson\n" +
            "Electrical engineering technician\n" +
            "Electrical linesworker (Aus)\n" +
            "Electrician (general)\n" +
            "Electronic engineering technician\n" +
            "Electronic equipment trades worker\n" +
            "Electronic instrument trades worker (general)\n" +
            "Electronics engineer\n" +
            "Electroplater\n" +
            "Embalmer\n" +
            "Emergency medicine specialist\n" +
            "Endocrinologist\n" +
            "Engineering patternmaker\n" +
            "Engraver\n" +
            "Enrolled nurse\n" +
            "Entertainer or variety artist\n" +
            "Entrepreneur\n" +
            "Environmental consultant\n" +
            "Environmental engineer\n" +
            "Environmental health officer\n" +
            "Environmental manager\n" +
            "Environmental research scientist\n" +
            "Exercise scientist\n" +
            "Facilities manager\n" +
            "Farm manager\n" +
            "Fashion designer\n" +
            "Fast food cook\n" +
            "Film and video producer\n" +
            "Finance broker\n" +
            "Financial institution branch manager\n" +
            "Financial investment adviser\n" +
            "Financial investment manager\n" +
            "Fire fighter\n" +
            "Fisheries officer\n" +
            "Fishing hand\n" +
            "Fitness instructor\n" +
            "Flight attendant\n" +
            "Floor finisher\n" +
            "Florist\n" +
            "Flying instructor\n" +
            "Food processing worker\n" +
            "Food technologist\n" +
            "Forensic scientist\n" +
            "Forester (Aus)\n" +
            "Forklift driver\n" +
            "Fruit and vegetable picker\n" +
            "Funeral director\n" +
            "Furniture finisher\n" +
            "Furniture removalist\n" +
            "Gallery or museum curator\n" +
            "Gallery or museum guide\n" +
            "Gallery or museum technician\n" +
            "Game developer\n" +
            "Gaming worker\n" +
            "Gardener (general)\n" +
            "Gasfitter\n" +
            "Gastroenterologist\n" +
            "General clerk\n" +
            "General medical practitioner\n" +
            "Geologist\n" +
            "Geophysicist\n" +
            "Geotechnical engineer\n" +
            "Glazier\n" +
            "Goldsmith\n" +
            "Grain oilseed or pasture grower\n" +
            "Grape grower\n" +
            "Graphic designer\n" +
            "Graphic pre-press trades worker\n" +
            "Greenkeeper\n" +
            "Gunsmith\n" +
            "Gynaecologist\n" +
            "Hairdresser\n" +
            "Handyperson\n" +
            "Health information manager\n" +
            "Health promotion officer\n" +
            "Helicopter pilot\n" +
            "Historian\n" +
            "Horse trainer\n" +
            "Hospital pharmacist\n" +
            "Hotel or motel manager\n" +
            "Human resource adviser\n" +
            "Human resource manager\n" +
            "Hydrologist\n" +
            "ICT business analyst\n" +
            "ICT project manager\n" +
            "ICT security specialist\n" +
            "ICT support technician\n" +
            "ICT systems test engineer\n" +
            "Immigration officer (Border Force officer)\n" +
            "Industrial designer\n" +
            "Industrial engineer\n" +
            "Industrial pharmacist\n" +
            "Information technology administrator\n" +
            "Insurance agent\n" +
            "Insurance broker\n" +
            "Intelligence officer\n" +
            "Intensive care specialist\n" +
            "Interior designer\n" +
            "Interpreter\n" +
            "Jackeroo/Jillaroo\n" +
            "Jewellery designer\n" +
            "Jockey\n" +
            "Joiner\n" +
            "Journalist\n" +
            "Judge\n" +
            "Kitchenhand\n" +
            "Laboratory manager\n" +
            "Laboratory technician\n" +
            "Landscape architect\n" +
            "Landscape gardener\n" +
            "Laundry worker (general)\n" +
            "Law clerk\n" +
            "Legal secretary\n" +
            "Librarian\n" +
            "Library assistant\n" +
            "Library technician\n" +
            "Lifeguard\n" +
            "Light technician\n" +
            "Locksmith\n" +
            "Logistics clerk\n" +
            "Machine shorthand reporter\n" +
            "Maintenance planner\n" +
            "Make up artist\n" +
            "Marine biologist\n" +
            "Marine fabricator\n" +
            "Marine surveyor\n" +
            "Market research analyst\n" +
            "Marketing specialist\n" +
            "Massage therapist\n" +
            "Master fisher\n" +
            "Materials engineer\n" +
            "Materials recycler\n" +
            "Materials technician\n" +
            "Mathematician\n" +
            "Meat inspector\n" +
            "Mechanical engineer\n" +
            "Mechanical engineering draftsperson\n" +
            "Mechanical engineering technician\n" +
            "Mechanical fitter\n" +
            "Mechatronic engineer\n" +
            "Medical administrator\n" +
            "Medical diagnostic radiographer\n" +
            "Medical laboratory scientist\n" +
            "Medical oncologist\n" +
            "Medical radiation therapist\n" +
            "Member of parliament\n" +
            "Mental health worker\n" +
            "Metal engineering process worker\n" +
            "Metal fabricator\n" +
            "Metal machinist (first class)\n" +
            "Metallurgist\n" +
            "Meteorologist\n" +
            "Meter reader\n" +
            "Microbiologist\n" +
            "Midwife\n" +
            "Migration agent (Aus)\n" +
            "Milliner\n" +
            "Miner\n" +
            "Mining engineer (excluding petroleum)\n" +
            "Mining production manager\n" +
            "Mining support worker\n" +
            "Minister of religion\n" +
            "Mixed crop and livestock farm worker\n" +
            "Model\n" +
            "Mortgage broker\n" +
            "Motion picture projectionist\n" +
            "Motor mechanic (general)\n" +
            "Motorcycle mechanic\n" +
            "Multimedia specialist\n" +
            "Music teacher (private tuition)\n" +
            "Musician (instrumental)\n" +
            "Nanny\n" +
            "Naturopath\n" +
            "Naval architect\n" +
            "Navy sailor\n" +
            "Navy technician\n" +
            "Network administrator\n" +
            "Network engineer\n" +
            "Neurologist\n" +
            "Neurosurgeon\n" +
            "Nuclear medicine technologist\n" +
            "Nurse educator\n" +
            "Nurse manager\n" +
            "Nurse practitioner\n" +
            "Nurseryperson\n" +
            "Nursing clinical director\n" +
            "Nursing support worker\n" +
            "Nutritionist\n" +
            "Obstetrician\n" +
            "Occupational health and safety adviser\n" +
            "Occupational therapist\n" +
            "Office manager\n" +
            "Offset printer\n" +
            "Ophthalmologist\n" +
            "Optical mechanic\n" +
            "Optometrist\n" +
            "Orthopaedic surgeon\n" +
            "Orthoptist\n" +
            "Orthotist or prosthetist\n" +
            "Osteopath\n" +
            "Otorhinolaryngologist\n" +
            "Outdoor adventure instructor\n" +
            "Paediatric surgeon\n" +
            "Paediatrician\n" +
            "Painting trades worker\n" +
            "Panelbeater\n" +
            "Park ranger\n" +
            "Parking inspector\n" +
            "Pastrycook\n" +
            "Pathologist\n" +
            "Patient care assistant\n" +
            "Payroll clerk\n" +
            "Personal assistant\n" +
            "Pest controller\n" +
            "Pet groomer\n" +
            "Petroleum engineer\n" +
            "Petrophysicist\n" +
            "Pharmacist\n" +
            "Pharmacologist\n" +
            "Pharmacy technician\n" +
            "Photographer\n" +
            "Photographer's assistant\n" +
            "Physical education (PE) teacher\n" +
            "Physicist\n" +
            "Physiotherapist\n" +
            "Picture framer\n" +
            "Plant mechanic\n" +
            "Plastic and reconstructive surgeon\n" +
            "Plastics technician\n" +
            "Plumber (general)\n" +
            "Podiatrist\n" +
            "Police officer\n" +
            "Policy analyst\n" +
            "Political scientist\n" +
            "Polymer factory worker\n" +
            "Postal delivery officer\n" +
            "Powder coater (spray painter)\n" +
            "Power generation plant operator\n" +
            "Precision instrument maker and repairer\n" +
            "Primary school teacher\n" +
            "Printing machinist\n" +
            "Prison officer\n" +
            "Private investigator\n" +
            "Process engineer\n" +
            "Process plant operator\n" +
            "Product examiner\n" +
            "Production manager (manufacturing)\n" +
            "Production or plant engineer\n" +
            "Project builder\n" +
            "Project or program administrator\n" +
            "Prop and scenery maker\n" +
            "Property developer\n" +
            "Property manager\n" +
            "Psychiatrist\n" +
            "Psychologist\n" +
            "Public relations professional\n" +
            "Publisher\n" +
            "Quantity Surveyor\n" +
            "Radiation oncologist\n" +
            "Radio presenter\n" +
            "Radio producer\n" +
            "Radiologist\n" +
            "Railway track worker\n" +
            "Real estate representative\n" +
            "Receptionist (general)\n" +
            "Records manager\n" +
            "Recreation officer (Aus)\n" +
            "Recruitment consultant\n" +
            "Recycling or rubbish collector\n" +
            "Registered nurse (community health)\n" +
            "Registered nurse (mental health)\n" +
            "Registered nurse (overview)\n" +
            "Renal medicine specialist\n" +
            "Resident medical officer\n" +
            "Retail buyer\n" +
            "Retail manager (general)\n" +
            "Retirement village manager\n" +
            "Rheumatologist\n" +
            "Road worker\n" +
            "Roof plumber\n" +
            "Roof tiler\n" +
            "Safety inspector\n" +
            "Sales and marketing manager\n" +
            "Sales assistant (general)\n" +
            "Sales representative\n" +
            "Saw maker and repairer\n" +
            "Sawmill or timber yard worker\n" +
            "Scaffolder\n" +
            "School principal\n" +
            "Screen printer\n" +
            "Seafood process worker\n" +
            "Secondary school teacher\n" +
            "Security officer\n" +
            "Security systems installer\n" +
            "Set designer\n" +
            "Settlement agent\n" +
            "Shearer\n" +
            "Sheep farmer\n" +
            "Sheetmetal trades worker\n" +
            "Shelf filler\n" +
            "Ship's engineer\n" +
            "Ship's master\n" +
            "Ship's officer\n" +
            "Shoe repairer\n" +
            "Shotfirer\n" +
            "Signwriter\n" +
            "Silversmith\n" +
            "Social worker\n" +
            "Soil scientist\n" +
            "Soldier\n" +
            "Solicitor\n" +
            "Solid plasterer\n" +
            "Sonographer\n" +
            "Sound technician\n" +
            "Special needs teacher\n" +
            "Speech pathologist (Aus)\n" +
            "Sports administrator\n" +
            "Sports coach or instructor\n" +
            "Sports development officer\n" +
            "Sportsperson\n" +
            "Statistician\n" +
            "Steel fixer\n" +
            "Stockbroking dealer\n" +
            "Stonemason\n" +
            "Storeperson\n" +
            "Streetsweeper operator\n" +
            "Structural engineer\n" +
            "Stunt performer\n" +
            "Supply and distribution manager\n" +
            "Surgeon (general)\n" +
            "Surveyor\n" +
            "Surveyor's assistant\n" +
            "Systems administrator\n" +
            "Systems analyst\n" +
            "Talent agent\n" +
            "Taxi driver\n" +
            "Teacher of the hearing impaired\n" +
            "Teacher of the sight impaired\n" +
            "Teachers' aide\n" +
            "Technical cable jointer\n" +
            "Telecommunications engineer\n" +
            "Textile production worker\n" +
            "Textile, clothing and footwear mechanic\n" +
            "Thoracic medicine specialist\n" +
            "Ticket collector or usher\n" +
            "Tool pusher\n" +
            "Tour guide\n" +
            "Tourist information officer\n" +
            "Trade union official\n" +
            "Train controller\n" +
            "Train driver\n" +
            "Training and development professional\n" +
            "Transit officer\n" +
            "Translator\n" +
            "Transport company manager\n" +
            "Transport engineer\n" +
            "Travel consultant\n" +
            "Tree faller\n" +
            "Truck driver (general)\n" +
            "Turf grower\n" +
            "Tyre fitter\n" +
            "University lecturer\n" +
            "Upholsterer\n" +
            "Urban and regional planner\n" +
            "Urologist\n" +
            "Valuer\n" +
            "Vascular surgeon\n" +
            "Vehicle body builder\n" +
            "Veterinarian\n" +
            "Veterinary nurse\n" +
            "Video editor\n" +
            "Vineyard worker\n" +
            "Visual arts and crafts professional\n" +
            "Visual merchandiser\n" +
            "Vocational Education and Training (VET) lecturer\n" +
            "Waiter\n" +
            "Wall and ceiling fixer\n" +
            "Wall and floor tiler\n" +
            "Warehouse administrator\n" +
            "Waste water or water plant operator\n" +
            "Watch and clock maker and repairer\n" +
            "Water inspector\n" +
            "Waterside worker\n" +
            "Web designer\n" +
            "Weight loss consultant\n" +
            "Welder (first class) (Aus)\n" +
            "Welfare centre manager\n" +
            "Welfare worker\n" +
            "Wine maker\n" +
            "Wood machinist\n" +
            "Wood turner\n" +
            "Wool classer\n" +
            "Youth worker\n" +
            "Zoologist";
}
