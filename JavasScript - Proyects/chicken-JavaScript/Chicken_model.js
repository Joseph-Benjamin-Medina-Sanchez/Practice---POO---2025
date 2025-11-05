class Chicken {
   /**
     @param {number} id 
     @param {string} name
      @param {string} color 
     @param {number} age 
     @param {boolean} isMolting 
     */
    constructor(id, name, color, age, isMolting) {
          this.id = id;
          this.name = name;
          this.color = color;
          this.age = age;
          this.isMolting = isMolting;
    }

     toString() {
          return `Chicken: id=${this.id}, name=${this.name}, color=${this.color}, age=${this.age}, isMolting=${this.isMolting}`;
    }
}
module.exports = Chicken;