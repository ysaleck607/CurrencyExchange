'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');

let stompClient = null;
let firstemail = null;
let secondemail = null;
let selectedUserId = null;
let User1 = null;
let User2 = null;

function connect(event, offerorUserId, demandeurUserId, offreurName, demandeurName) {
    // Définition des variables à partir des paramètres
    let user1Id, user2Id;

    // Vérifier quelle adresse e-mail correspond à l'utilisateur actuel
    if (offerorUserId === localStorage.getItem('userId')) {
        firstemail = offreurName;
        user1Id = offerorUserId;
        secondemail = demandeurName;
        user2Id = demandeurUserId;
    } else {
        firstemail = demandeurName;
        user1Id = demandeurUserId;
        secondemail = offreurName;
        user2Id = offerorUserId;
    }

    User1 = {
        email: firstemail,
        id: user1Id
    };

    User2 = {
        email: secondemail,
        id: user2Id
    };

    // Cacher le formulaire de saisie du nom d'utilisateur et afficher la page de chat
    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    // Connexion au serveur WebSocket
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
    if (event) {
        event.preventDefault();
    }
}

function onConnected() {
    stompClient.subscribe(`/user/${firstemail}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onMessageReceived);

    // register the connected user
    stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({email: firstemail})
    );
    document.querySelector('#connected-user-fullname').textContent = firstemail;
    findAndDisplayConnectedUsers().then();
}

async function findAndDisplayConnectedUsers() {
    const connectedUsersList = document.getElementById('connectedUsers');
    connectedUsersList.innerHTML = '';

    appendUserElement(User2, connectedUsersList);
}

function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.email;

    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
    userImage.alt = user.email;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.email;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);
    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);

    connectedUsersList.appendChild(listItem);
}

function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectedUserId = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';
}

function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId == User1.id) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${User1.id}/${User2.id}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.content);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}

function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: User1.id,
            recipientId: User2.id,
            content: messageInput.value.trim(),
            timestamp: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(User1.id, messageInput.value.trim());
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}

async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        document.querySelector(`#${selectedUserId}`).classList.add('active');
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

function onLogout() {
    window.location.reload();
    window.location.href = 'TableauBord.html';
}

// Vérifie si les paramètres nécessaires sont présents dans l'URL pour une connexion directe au chat
const urlParams = new URLSearchParams(window.location.search);
const offerorUserId = urlParams.get('offerorUserId');
const demandeurUserId = urlParams.get('demandeurUserId');
const offreurName = urlParams.get(decodeURIComponent('offreurName'));
const demandeurName = urlParams.get(decodeURIComponent('demandeurName'));

if (offerorUserId && demandeurUserId && offreurName && demandeurName) {
    // Appel de la fonction connect avec les paramètres nécessaires
    connect(null, offerorUserId, demandeurUserId, offreurName, demandeurName);
} else {
    // Afficher le formulaire de saisie du nom d'utilisateur si les paramètres ne sont pas présents
    usernamePage.classList.remove('hidden');
    chatPage.classList.add('hidden');
}

usernameForm.addEventListener('submit', connect, true); // step 1
messageForm.addEventListener('submit', sendMessage, true);
logout.addEventListener('click', onLogout, true);
window.onbeforeunload = () => onLogout();
