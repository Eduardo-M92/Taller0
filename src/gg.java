import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class gg 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		String[] names = new String[1000];
		String[] enemyName = new String[1000];
		String[] enemyClass = new String[1000];
		String[] password = new String[1000];
		String[] spells = new String[1000];
		String[][] playerSpells = new String[1000][1000];
		int[] attackPower = new int[1000];
		int[] numHechizos = new int[1000];
		int[] enemyHealt = new int[1000];
		int[] enemyvelocity = new int[1000];
		int[] enemyAttack  = new int[1000];
		int[] enemyXP = new int[1000];
		int[] enemyPer = new int[1000];
		double[] healt = new double[1000];
		double[] attack = new double[1000];
		double[] defense = new double[1000];
		double[] speed = new double[1000];
		double[] xp= new double[1000];
		/*
		 * cantPlayers, cantSpells, cantEnemies: save amount
		 */
		int cantPlayers = readPlayers(names, password, numHechizos, healt, attack, defense, speed, xp);
		int cantSpells = readSpells(spells, attackPower, playerSpells, names, cantPlayers);
		int cantEnemies = readEnemies(enemyName, enemyClass, enemyHealt, enemyvelocity, enemyAttack, enemyPer, enemyXP);
		
		boolean logIn = false;
		System.out.println("Bienvenido a ||Magic Art Online||");
		while(!logIn)
		{
			System.out.println("-----------------------------------------");
			System.out.println("Iniciar sesión");
			System.out.println("-----------------------------------------");
			int userLogIn = logIn(names, password, cantPlayers);
			/*
			 * If userLogIn = -1 the user does not have an account and has to register
			 * Else open user or admin menu.
			 */
			if(userLogIn==-1)
			{
				register(names, password, cantPlayers, numHechizos, healt, attack, defense, speed, xp);
				cantPlayers+=1;
				
			}
			else
			{
				logIn = true;
				if (userLogIn!=-2)
				{
					userMenu(playerSpells, cantSpells, spells, attackPower, userLogIn, cantEnemies, cantPlayers, names, password, numHechizos, healt, attack, defense, speed, xp, enemyName, enemyClass, enemyHealt, enemyvelocity, enemyAttack, enemyPer, enemyXP);
				}else { adminMenu(); }
			}
		}
		
	}
	/*
	* this function read archive jugadores.txt
	*/
	public static int readPlayers(String[] names,String[] password,int[] numHechizos,double[] healt,double[] attack,double[] defense,double[] speed,double[] xp) throws FileNotFoundException
	{
		int cont=0;
		Scanner arch = new Scanner(new File("Jugadores.txt"));
		while(arch.hasNextLine())
		{
			String[] parts = arch.nextLine().split(",");
			names[cont] = parts[0];
			password[cont] = parts[1];
			healt[cont] = Double.parseDouble(parts[2]);
			attack[cont] = Double.parseDouble(parts[3]);
			defense[cont] = Double.parseDouble(parts[4]);
			speed[cont] = Double.parseDouble(parts[5]);
			numHechizos[cont] = Integer.parseInt(parts[6]);
			xp[cont] = Double.parseDouble(parts[7]);
			cont++;
		}
		return cont;
	}
	/*
	 * this function read archive Hechizos.txt and HechizosJugadores.txt
	 */
	public static int readSpells(String[] spells, int[] attackPower,String[][] playerSpells, String[] names,int cantPlayers) throws FileNotFoundException
	{
		int cont = 0;
		int contPS;
		Scanner arch1 = new Scanner(new File("Hechizos.txt"));
		while(arch1.hasNextLine())
		{
			String[] parts = arch1.nextLine().split(",");
			spells[cont] = parts[0];
			attackPower[cont] = Integer.parseInt(parts[1]);
			cont++;
		}
		
		Scanner arch2 = new Scanner(new File("HechizosJugadores.txt"));
		while(arch2.hasNextLine())
		{
			contPS = 0;
			String[] parts = arch2.nextLine().split(",");
			int namePos = findName(names, parts[0], cantPlayers);
			while(playerSpells[contPS][namePos]!=null)
			{
				contPS++;
			}
			playerSpells[contPS][namePos] = parts[1];
		}
		
		return cont;
	}
	/*
	 * this function read archive Enemigo.txt
	 */
	public static int readEnemies(String[] enemyName,String[] enemyClass,int[] enemyHealt,int[] enemyvelocity,int[] enemyAttack, int[] enemyPer, int[] enemyXP) throws FileNotFoundException
	{
		int cont = 0;
		Scanner arch = new Scanner(new File("Enemigo.txt"));
		while(arch.hasNextLine())
		{
			String[] parts = arch.nextLine().split(",");
			enemyName[cont] = parts[0];
			enemyHealt[cont] = Integer.parseInt(parts[1]);
			enemyAttack[cont] = Integer.parseInt(parts[2]); ;
			enemyClass[cont] = parts[3];
			switch (parts[3])
			{
			case "S":
				enemyPer[cont] = 1;
				break;
			case "A":
				enemyPer[cont] = 10;
				break;
			case "B":
				enemyPer[cont] = 25;
				break;
			case "C":
				enemyPer[cont] = 50;
				break;
			case "F":
				enemyPer[cont] = 75;
				break;
			}
			enemyvelocity[cont] = Integer.parseInt(parts[4]);
			cont++;
		}
		return cont;
	}
	/*
	 * this function searches for a name within a vector
	 * return position inside vector.
	 */
	public static int findName(String[] names,String name,int cantPlayers)
	{
		int cont;
		for(cont=0;cont<cantPlayers;cont++)
		{
			if(names[cont].equals(name))
			{
				break;
			}
		}
		if(cont == cantPlayers)
		{
			return -1;
		}
		else
		{
			return cont;
		}
	}
	/*
	 * verify user
	 * return -1 if user don't exist and will get register
	 * return -2 if user is admin
	 * else return position for user in the vector
	 */
	public static int logIn(String[] names, String[] password, int cantPlayers)
	{
		int pos=-1;
		Scanner sc = new Scanner(System.in);
		String option;
		String pass;
		String res;
		boolean logIn = false;
		while(!logIn)
		{
			System.out.print("Ingrese nombre de usuario: ");
			option = sc.next();
			pos = findName(names, option, cantPlayers);
			if(pos==-1)
			{
				System.out.println("404.Usuario no encontrado");
				System.out.print("¿Quiere registrar un nuevo usuario?(si - no): ");
				res = sc.next().toLowerCase();
				if(res.equals("si"))
				{
					break;
				}
			}
			else
			{
				System.out.print("Ingrese su contraseña: ");
				pass = sc.next();
				if(password[pos].equals(pass))
				{
					System.out.println("Inicio de sesión exitoso");
					logIn = true;
				}
				else
				{
					System.out.println("-----------------------------------------");
					System.out.println("Contraseña incorrecta");
					System.out.println("-----------------------------------------");
				}
			}
		}
		return pos;
	}
	/*
	 * register new user
	 * and all status user = 0
	 */
	public static void register(String[] names,String[] password, int cantPlayers, int[] numHechizos,double[] healt,double[] attack,double[] defense,double[] speed,double[] xp)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("-----------------------------------------");
		System.out.println("Menu de registro");
		System.out.println("-----------------------------------------");
		System.out.print("Ingresar nombre de usuario: ");
		names[cantPlayers] = sc.next();
		System.out.print("Ingresar contraseña: ");
		password[cantPlayers] = sc.next();
	}
	/*
	 * administrative menu
	 */
	public static void adminMenu()
	{
		
	}
	
	public static void userMenu(String[][] playerSpells,int cantSpells,String[] spells,int[] attackPower, int userPos, int cantEnemies,int cantPlayer , String[] names,String[] password,int[] numHechizos,double[] healt,double[] attack,double[] defense,double[] speed,double[] xp, String[] enemyName,String[] enemyClass,int[] enemyHealt,int[] enemyvelocity,int[] enemyAttack, int[] enemyPer, int[] enemyXP)
	{
		String name;
		int find;
		Scanner sc = new Scanner(System.in);
		String option;
		int damage = 0;
		boolean exit = false;
		while(!exit)
		{
			System.out.println("Menu de acciones");
			System.out.println("a) Pelear contra un enemigo");
			System.out.println("b) Aprender echizo");
			System.out.println("c) Ver Stats de un jugador");
			System.out.println("d) Ver Stats de echizos");
			System.out.println("e) TOP 10 jugadores con mas XP");
			System.out.println("f) Salir y guardar");
			System.out.print("Ingrese opción: ");
			option = sc.next();
			switch(option)
			{
			case "a":
				fight(userPos, cantEnemies, cantPlayer, names, password, numHechizos, healt, attack, defense, speed, xp, enemyName, enemyClass, enemyHealt, enemyvelocity, enemyAttack, enemyPer, enemyXP, playerSpells, cantSpells, spells, attackPower);
				break;
			case "b":
				learn(userPos, cantSpells, spells, playerSpells, numHechizos, xp);
				break;
			case "c":
				System.out.print("Ingrese nombre de jugador: ");
				name=sc.next();
				find = findName(names, name, cantPlayer);
				if(find ==-1)
				{
					System.out.println("Jugador no encontrado");
				}
				else
				{
					/*
					 * print user status
					 */
					System.out.println("-----------------------------------------");
					System.out.println("Stats de el jugador "+names[find]);
					System.out.println("Vida: "+healt[find]);
					System.out.println("Ataque: "+attack[find]);
					System.out.println("Defensa: "+defense[find]);
					System.out.println("Velocidad de ataque: "+speed[find]);
					System.out.println("XP: "+xp[find]);
					System.out.println("Cantidad de echizos: "+numHechizos[find]);
					System.out.println("-----------------------------------------");
				}
				break;
			case "d":
				System.out.print("Ingrese nombre de jugador: ");
				name=sc.next();
				find = findName(names, name, cantPlayer);
				System.out.println("-----------------------------------------");
				if(find ==-1)
				{
					System.out.println("Jugador no encontrado");
				}
				else
				{
					System.out.println("Hechizos de "+names[find]+": ");
					for(int a=0;a<numHechizos[find];a++)
					{
						int b;
						for(b=0;b<cantSpells;b++)
						{
							if(spells[b].equals(playerSpells[a][find]))
							{
								damage = attackPower[b];
								break;
							}
						}
						System.out.println((a+1)+") "+playerSpells[a][find]+" daño: "+damage);
					}
				}
				System.out.println("-----------------------------------------");
				break;
			case "e":
				int[] ranking = ranking(xp,cantPlayer,names);
				break;
			case "f":
				exit=true;
				break;
			}
		}
	}
	
	public static int[] ranking(double[] xp,int cantPlayer,String[] names)
	{

		return null;
		
	}
	
	public static void fight(int userPos, int cantEnemies,int cantPlayer , String[] names,String[] password,int[] numHechizos,double[] healt,double[] attack,double[] defense,double[] speed,double[] xp, String[] enemyName,String[] enemyClass,int[] enemyHealt,int[] enemyvelocity,int[] enemyAttack, int[] enemyPer, int[] enemyXP,String[][] playerSpells,int cantSpells,String[] spells,int[] attackPower)
	{
		Scanner sc = new Scanner(System.in);
		String option;
		System.out.println("-----------------------------------------");
		System.out.println("Pelea!");
		System.out.println("-----------------------------------------");
		System.out.println("Peleas disponibles |JcE|, |JvJ|");
		System.out.println("|JcE| Jugador contra entorno");
		System.out.println("|JvJ| Jugador contra jugador");
		System.out.print("Ingrese tipo de pelea: ");
		option = sc.next().toLowerCase();
		switch(option)
		{
		case "jce":
			int numEnemy ;
			int randomize = -1;
			boolean ready = false;
			while(!ready)
			{
				numEnemy = (int) (Math.random()*101);
				randomize = (int) (Math.random()*cantEnemies);
				if(enemyPer[randomize]>=numEnemy)
				{
					ready = true;
				}
			}
			int points = fightPcE(playerSpells, cantSpells, spells, attackPower,enemyClass[randomize], enemyHealt[randomize], enemyAttack[randomize], enemyvelocity[randomize], enemyName[randomize], numHechizos[userPos], healt[userPos], attack[userPos], defense[userPos], speed[userPos], userPos);
			/*
			 * Assigned win points
			 */
			while (points>0)
			{
				System.out.println("Los puntos se pueden repartir en:");
				System.out.println("a) Vida");
				System.out.println("b) Ataque");
				System.out.println("c) Defensa");
				System.out.println("d) Velocidad de ataque");
				System.out.print("Ingrese su preferncia: ");
				String asigOp = sc.next().toLowerCase();
				System.out.print("Cuantos puntos quiere asignar (quedan: "+points+"): ");
				int asigPoint = sc.nextInt();
				
				while (!(asigPoint<=points))
				{
					System.out.println("Cantidad invalida");
					System.out.print("Cuantos puntos quiere asignar (quedan: "+points+"): ");
					asigPoint = sc.nextInt();
				}
				points-=asigPoint;
				switch(asigOp)
				{
				case "a":
					healt[userPos]+=asigPoint;
					break;
				case "b":
					attack[userPos]+=asigPoint;
					break;
				case "c":
					defense[userPos]+=asigPoint;
					break;
				case "d":
					speed[userPos]+=asigPoint;
					break;
				}
			}
			
			break;
		case "jvj":
			int bPlayer = (int) Math.random()*cantPlayer;
			int aPlayer = userPos;
			while(bPlayer==userPos)
			{
				bPlayer = (int) Math.random()*cantPlayer;
			}
			String bName = names[bPlayer];
			double bhealt = healt[bPlayer];
			double bAttack = attack[bPlayer];
			double bDefence = defense[bPlayer];
			double bSpeed = speed[bPlayer];
			String aName = names[aPlayer];
			double ahealt = healt[aPlayer];
			double aAttack = attack[aPlayer];
			double aDefence = defense[aPlayer];
			double aSpeed = speed[aPlayer];
			
			if(fightJvJ(playerSpells,cantSpells,spells,attackPower,numHechizos,aName,bName,ahealt,aSpeed,bhealt,aAttack,bAttack,aDefence,bDefence,bSpeed,aPlayer,bPlayer))
			{
				System.out.println("Has ganado la batalla y 250 de XP");
				xp[userPos]+=250;
			}
			else { System.out.println("Has perdido vuelve a jugar");; }
			break;
		}
		
	}
	/*
	 * if you win return points depended enemy class
	 * return -1 if you lose
	 */
	public static int fightPcE(String[][] playerSpells,int cantSpells,String[] spells,int[] attackPower ,String enemyClass,int enemyHealt,int enemyAttack,int enemyvelocity,String enemyName,int numHechizos,double healt,double attack,double defense,double speed, int userPos)
	{
		System.out.println("Lucha contra "+enemyName+" clase "+enemyClass);
		double damage;
		int first = 0;
		while(enemyHealt>0 && healt>0)
		{
			if(enemyvelocity>speed && first==0 || first%2==0)
			{
				System.out.println("El enemigo ataca y te madrea con "+(enemyAttack-defense)+" de daño...");
				healt-=(enemyAttack-defense);
			}
			else
			{
				damage = attackType("player",attack,playerSpells,cantSpells,spells,speed,attackPower,userPos,numHechizos);
				System.out.println("has propinado "+damage+" de daño al enemigo");
				enemyHealt-=damage;
			}
			first++;
		}
		if(healt<=0)
		{
			System.out.println("-----------------------------------------");
			System.out.println("Has perdido, Juega nuevamente");
			System.out.println("-----------------------------------------");
			return -1;
			
		}
		else
		{
			int stats=0;
			System.out.println("-----------------------------------------");
			System.out.println("Has ganado");
			System.out.println("-----------------------------------------");
			switch(enemyClass)
			{
			case "S":
				stats = 20;
				break;
			case "A":
				stats = 10;
				break;
			case "B":
				stats = 5;
				break;
			case "C":
				stats = 2;
				break;
			case "F":
				stats = 1;
				break;
			}
			System.out.println("Tienes "+stats+" puntos para repartir");
			return stats;
		}
		
		
	}
	
	/*
	 * return true if you win
	 * else return false
	 */
	public static boolean fightJvJ(String[][] playerSpells,int cantSpells,String[] spells,int[] attackPower,int[] numHechizos,String aName,String bName,double ahealt, double aSpeed,double bhealt,double aAttack,double bAttack,double aDefence,double bDefence,double bSpeed,int aPlayer, int bPlayer)
	{
		int first = 0;
		double damage;
		while(ahealt>0 && bhealt>0)
		{
			if(aSpeed>=bSpeed&& first == 0 || first%2==0 || aSpeed<bSpeed&& first==1)
			{
				damage = attackType("player",aAttack,playerSpells,cantSpells,spells,aSpeed,attackPower,aPlayer,numHechizos[aPlayer]);
				bhealt=(bDefence+bhealt)-(damage);
				System.out.println("Has propinado "+damage+" de daño a "+bName);
			}
			else
			{
				damage = attackType(bName,bAttack,playerSpells,cantSpells,spells,bSpeed,attackPower,bPlayer,numHechizos[bPlayer]);
				ahealt = (aDefence+ahealt)-(damage);
			}
			first++;
		}
		if(ahealt>0)
		{
			return true;
		}else { return false; }
	}
	/*
	 * select menu attack type(Hechizo or Basico)
	 * return damage depending attack type
	 */	
	public static double attackType(String typeF,double attack,String[][]playerSpells,int cantSpells,String[] spells,double speed,int[] attackPower,int userPos,int numHechizos)
	{
		int randomize;
		if(typeF.equals("player"))
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("¿Como atacara?");
			System.out.println("a) Basico");
			System.out.println("b) Hechizo");
			String option = sc.next().toLowerCase();
			
			if(option.equals("a"))
			{
				System.out.println("Atacando con ataque basico...");
				return attack;
			}
			else
			{
				while(true)
				{
					if(option.equals("b"))
					{
						System.out.println("Hechizos diponibles");
						for(int a=0;a<numHechizos;a++)
						{
							System.out.println((a+1)+") "+playerSpells[a][userPos]);
						}
						System.out.print("Ingrese nombre de echizo: ");
						String SpellName = sc.next().toLowerCase();
						int a;
						for(a=0;a<cantSpells;a++)
						{
							if(spells[a].toLowerCase().equals(SpellName))
							{
								return attackPower[a];
							}
						}
						if(a==cantSpells)
						{
							System.out.println("Ingreso incorrecto");
						}
					}
				}
			}
		}
		else
		{
			randomize = (int) (Math.random()*2);
			if(randomize==1)
			{
				System.out.println(typeF+" a atacando con ataque basico...");
				return attack;
			}
			else
			{
				randomize = (int) (Math.random()*numHechizos);
				System.out.println(typeF+" a usado "+spells[randomize]+" y propino daño = "+attackPower[randomize]);
				return attackPower[randomize];
			}
		}
	}
	
	/*
	 * learn new Spell
	 */
	public static void learn(int userPos,int cantSpells,String[] spells,String[][] playerSpells,int[] numHechizos,double[] xp)
	{
		int repeat = 0;
		if(cantSpells>numHechizos[userPos]&& xp[userPos]>=1000)
		{
			for (int a=0;a<cantSpells;a++)
			{
				repeat = 0;
				for(int b=0;b<numHechizos[userPos];b++)
				{
					if(spells[a].equals(playerSpells[b][userPos]))
					{
						repeat++;
					}
				}
				if(repeat==0)
				{
					System.out.println("Hechizo "+spells[a]+" aprendido");
					playerSpells[numHechizos[userPos]][userPos] = spells[a];
					numHechizos[userPos]+=1;
					xp[userPos]-=1000;
					repeat=a;
					break;
				}
			}
		}
		else
		{
			System.out.println("No se puede aprender hechizos baja XP o maximo de echizos existentes");
		}
	}
}
