import flask, json
from array import array
from flask import Flask, request

app= Flask('__main__',template_folder="", static_folder="", root_path="", static_url_path="")
msgs=[];

@app.route('/')
def index_page():
    print("Hello")
    return("Hello")



@app.route('/json/<number>')
def prikaz_jednog(number=None):
    try:
        with open("podaci.json") as f:
            data= json.load(f)
            n= int(number)
            return str(data[n])
    except(Exception):
        return "Greska"


@app.route('/json')
def ret_json():
    return flask.send_file("podaci.json")

@app.route('/json/del/',methods=['POST', 'GET'])
def del_obj_json():
    delID = request.form.get('id')
    print (delID) 
    try:
        with open("podaci.json", "r") as f:
            data=json.load(f)
            data.pop(int(delID))
    except(Exception):
        return "Greska pri ucitavanju json fajla za citanje"
    try:
        with open("podaci.json", "w") as f:
            json.dump(data, f, indent=2)
            return "Uspesno brisanje";

    except(Exception):
        return "Greska pri ucitavanju json fajla za pisanje"



@app.route('/json/add/',methods=['POST', 'GET'])
def nova_lista_json_adder():
    nova_lista ={}
    nova_lista_ime = request.form.get('ime_liste')
    try:
        sviid=[]
        with open("podaci.json", "r") as f:
            data=json.load(f)
            id_test = 0
            for el in data:
                sviid.append(int(el['id']))
            while True:
                if id_test in sviid:
                    id_test=id_test+1
                else:
                    break
                    
            nova_lista['id']= str(id_test) 
            nova_lista['ime']=nova_lista_ime
            data.append(nova_lista)
    except(Exception):
        return "Greska pri ucitavanju json fajla za citanje"
    try:
         with open("podaci.json", "w") as f:
            json.dump(data, f, indent=2)
            return "Uspesno dodavanje";

    except(Exception):
         return "Greska pri ucitavanju json fajla za pisanje"

@app.route('/json/edit/',methods=['POST', 'GET'])
def nova_lista_json_edit():

    lista_ime_edit = request.form.get('ime_liste_edit')
    lista_id_edit = request.form.get('id_liste_edit')
    print(lista_ime_edit)
    print(lista_id_edit)
    try:
        with open("podaci.json", "r") as f:
            data=json.load(f)
            for el in data:
                if str(el['id']) == lista_id_edit:
                    el['ime'] = lista_ime_edit
            print(data)
    except(Exception):
        return "Greska pri ucitavanju json fajla za citanje"
    try:
        with open("podaci.json", "w") as f:
            json.dump(data, f, indent=2)
            return "Uspesno dodavanje";

    except(Exception):
          return "Greska pri ucitavanju json fajla za pisanje"
    
            

    

app.run('0.0.0.0','5000')
