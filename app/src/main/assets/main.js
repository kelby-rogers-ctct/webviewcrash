import * as THREE from "three";

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  75,
  window.innerWidth / window.innerHeight,
  0.1,
  10
);
camera.position.z = 5;

const light = new THREE.DirectionalLight();
scene.add(light);

const ambientLight = new THREE.AmbientLight();
scene.add(ambientLight);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

let cube;
let geo = new THREE.BoxGeometry();
let material = new THREE.MeshPhongMaterial({ color: "green" });
cube = new THREE.Mesh(geo, material);

scene.add(cube);

const dialog = document.querySelector("#dialog");
dialog.showModal();

window.addEventListener("touchstart", closeDialogAndAnimate);
window.addEventListener("click", closeDialogAndAnimate);

function closeDialogAndAnimate() {
  dialog.close();
  animate();
}

// Animation loop
function animate() {
  requestAnimationFrame(animate);

  // Rotate the cube
  if (cube) {
    cube.rotation.x += 0.01;
    cube.rotation.y += 0.01;
  }

  renderer.render(scene, camera);
}

// Handle window resizing
window.addEventListener("resize", () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});
